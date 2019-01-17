package com.example.qrcode.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.example.qrcode.R;
import com.example.qrcode.adapter.ExpressDynamicAdapter;
import com.example.qrcode.bean.ExpressDynamic;
import com.example.qrcode.bean.ExpressScanHistory;
import com.example.qrcode.dao.ExpressDBManager;
import com.example.qrcode.listener.OnRequestExpressInfoListener;
import com.example.qrcode.model.Express100API;
import com.example.qrcode.model.ExpressCompanyModel;
import com.example.qrcode.view.CircleImageView;

import java.util.List;

public class ExpressDetailActivity extends AppCompatActivity {
    private static final String TAG = ExpressDetailActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private CircleImageView mCivCompany;
    private TextView mTvCompany, mTvExpressNo;
    private RecyclerView mRvExpressDynamic;
    private ExpressDynamicAdapter mAdapter;
    private Express100API mExpressApi;
    private TextView mTvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_detail);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCivCompany = findViewById(R.id.civ_company);
        mTvCompany = findViewById(R.id.tv_company);
        mTvExpressNo = findViewById(R.id.tv_express_no);

        mRvExpressDynamic = findViewById(R.id.recycler_express);
        mRvExpressDynamic.setLayoutManager(new LinearLayoutManager(this));

        mTvEmpty = findViewById(R.id.tv_empty);

        mExpressApi = new Express100API();

        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        if (null != intent) {
            String result = intent.getStringExtra("result");
            if (!TextUtils.isEmpty(result)) {
                mTvExpressNo.setText(result);
                // 搜索记录插入数据库
                ExpressDBManager.getInstance().insertExpress(new ExpressScanHistory(result));

                mExpressApi.queryExpressInfoByKuaidi100(result, new OnRequestExpressInfoListener() {
                    @Override
                    public void onResponseComCode(String companyCode) {
                        String companyName = ExpressCompanyModel.getInstance().getCompanyName(companyCode);
                        if (!TextUtils.isEmpty(companyName)) {
                            mTvCompany.setText(companyName);
                        }
                        RequestOptions options = new RequestOptions()
                                //.placeholder(R.drawable.img_default)// 图片加载出来前，显示的图片
                                //.fallback(R.drawable.img_blank) // url为空的时候,显示的图片
                                .error(R.mipmap.icon_express_default);// 图片加载失败后，显示的图片
                        Glide.with(ExpressDetailActivity.this)
                                .load("https://cdn.kuaidi100.com/images/all/56/" + companyCode + ".png")
                                .apply(options)
                                .into(mCivCompany);
                    }

                    @Override
                    public void onResponseDynamic(List<ExpressDynamic> list) {
                        refreshExpressDynamicList(list);
                    }

                    @Override
                    public void onErrorResponse() {
                        expressDynamicListEmpty(true);
                    }
                });
            }
        }
    }

    private void refreshExpressDynamicList(List<ExpressDynamic> list) {
        mAdapter = new ExpressDynamicAdapter(this, list);
        mRvExpressDynamic.setAdapter(mAdapter);

        expressDynamicListEmpty(null == list || list.isEmpty());
    }

    private void expressDynamicListEmpty(boolean empty) {
        mRvExpressDynamic.setVisibility(empty ? View.GONE : View.VISIBLE);
        mTvEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();// 真正实现回退功能的代码
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
