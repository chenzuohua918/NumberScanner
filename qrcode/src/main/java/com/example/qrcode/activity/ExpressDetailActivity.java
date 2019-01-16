package com.example.qrcode.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.qrcode.R;
import com.example.qrcode.bean.ExpressDynamic;
import com.example.qrcode.bean.ExpressScanHistory;
import com.example.qrcode.dao.ExpressDBManager;
import com.example.qrcode.listener.OnRequestExpressInfoListener;
import com.example.qrcode.model.Express100API;
import com.example.qrcode.utils.Logcat;

import java.util.List;

public class ExpressDetailActivity extends AppCompatActivity {
    private static final String TAG = ExpressDetailActivity.class.getSimpleName();

    private Express100API mExpressApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_detail);

        mExpressApi = new Express100API();

        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        if (null != intent) {
            String result = intent.getStringExtra("result");
            if (!TextUtils.isEmpty(result)) {
                // 插入数据库
                ExpressDBManager.getInstance().insertExpress(new ExpressScanHistory(result));

                mExpressApi.queryExpressInfoByKuaidi100(result, new OnRequestExpressInfoListener() {
                    @Override
                    public void onResponse(List<ExpressDynamic> list) {
                        for (ExpressDynamic express : list) {
                            Logcat.d(TAG, "date = " + express.date);
                            Logcat.d(TAG, "dynamic = " + express.dynamic);
                            Toast.makeText(ExpressDetailActivity.this, express.dynamic, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onErrorResponse() {

                    }
                });
            }
        }
    }
}
