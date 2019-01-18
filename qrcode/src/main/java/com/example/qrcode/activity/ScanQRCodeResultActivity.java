package com.example.qrcode.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qrcode.R;
import com.example.qrcode.base.BaseActivity;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class ScanQRCodeResultActivity extends BaseActivity {
    private ImageView mImageView;
    private TextView mTvResult;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode_result;
    }

    @Override
    public void onViewCreated(Bundle savedInstanceState) {
        mImageView = findViewById(R.id.iv_qrcode);
        mTvResult = findViewById(R.id.tv_result);

        showResultQRCode();
    }

    private void showResultQRCode() {
        Intent intent = getIntent();
        if (null != intent) {
            final String result = intent.getStringExtra("result");
            if (!TextUtils.isEmpty(result)) {
                mTvResult.setText(result);

                new AsyncTask<Void, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Void... params) {
                        return QRCodeEncoder.syncEncodeQRCode(result, BGAQRCodeUtil.dp2px(ScanQRCodeResultActivity.this, 150));
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        if (bitmap != null) {
                            mImageView.setImageBitmap(bitmap);
                        } else {
                            Toast.makeText(ScanQRCodeResultActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();
            }
        }
    }
}
