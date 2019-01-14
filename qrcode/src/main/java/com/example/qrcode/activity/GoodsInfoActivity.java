package com.example.qrcode.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qrcode.R;
import com.example.qrcode.adapter.ShopAdapter;
import com.example.qrcode.bean.Shop;
import com.example.qrcode.utils.GoodsErrorCode;
import com.example.qrcode.utils.Logcat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class GoodsInfoActivity extends AppCompatActivity {
    private static final String TAG = GoodsInfoActivity.class.getSimpleName();

    private ImageView mIvBarCode;
    private TextView mGoodsInfo;
    private RecyclerView mRvShop;
    private ShopAdapter mShopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);

        mIvBarCode = findViewById(R.id.iv_barcode);
        mGoodsInfo = findViewById(R.id.tv_goods_info);
        mRvShop = findViewById(R.id.recycler_shop);
        mRvShop.setLayoutManager(new LinearLayoutManager(this));
        mRvShop.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = 15;
            }
        });

        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        if (null != intent) {
            final String result = intent.getStringExtra("result");
            if (!TextUtils.isEmpty(result)) {
                showBarCode(result);
                requestGoodsInfo(result);
            }
        }
    }

    private void showBarCode(final String result) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                int width = BGAQRCodeUtil.dp2px(GoodsInfoActivity.this, 200);
                int height = BGAQRCodeUtil.dp2px(GoodsInfoActivity.this, 80);
                return QRCodeEncoder.syncEncodeBarcode(result, width, height, 0);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    mIvBarCode.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(GoodsInfoActivity.this, "生成条底部不带文字形码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void requestGoodsInfo(String goodsCode) {
        if (goodsCode.matches("^[0-9]*$")) {
            String url = "http://api.juheapi.com/jhbar/bar?appkey=" + getString(R.string.appkey) + "&pkg=" + getPackageName() + "&barcode=" + goodsCode + "&cityid=1";
            Logcat.d(TAG, "url = " + url);
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Logcat.d(TAG, "请求结果：" + s);
                    handleJsonResult(s);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyError.printStackTrace();
                    Logcat.e(TAG, "请求失败：" + volleyError.getMessage());
                }
            });
            Volley.newRequestQueue(this).add(request);
        } else {
            Logcat.e(TAG, "商品码中存在非数字字符");
        }
    }

    private void handleJsonResult(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            int errorCode = jsonObject.getInt("error_code");
            String reason = jsonObject.getString("reason");
            switch (errorCode) {
                case GoodsErrorCode.CODE_SUCCESS:// 请求成功
                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONObject summary = result.getJSONObject("summary");

                    StringBuffer sb = new StringBuffer();
                    sb.append("编号：" + summary.getString("barcode"));
                    sb.append("\n名称：" + summary.getString("name"));
                    int shopNum = summary.getInt("shopNum");
                    sb.append("\n线下商店数量：" + shopNum);
                    int eshopNum = summary.getInt("eshopNum");
                    sb.append("\n线上商店数量：" + eshopNum);
                    sb.append("\n价格：" + summary.getString("interval"));
                    mGoodsInfo.setText(sb.toString());

                    if (shopNum > 0) {
                        JSONArray shopArray = result.getJSONArray("shop");
                        List<Shop> shopList = new ArrayList<>();
                        JSONObject object;
                        for (int i = 0, size = shopArray.length(); i < size; i++) {
                            object = (JSONObject) shopArray.get(i);
                            shopList.add(new Shop(object.getString("shopname"), "￥" + object.getString("price")));
                        }
                        loadShopData(shopList);
                    }
                    break;
                case GoodsErrorCode.CODE_ERROR_BARCODE:
                case GoodsErrorCode.CODE_UNKNOWN_BARCODE:
                default:
                    mGoodsInfo.setText(reason);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadShopData(List<Shop> shopList) {
        if (mShopAdapter == null) {
            mShopAdapter = new ShopAdapter(this, shopList);
            mRvShop.setAdapter(mShopAdapter);
        } else {
            mShopAdapter.setData(shopList);
        }
    }
}
