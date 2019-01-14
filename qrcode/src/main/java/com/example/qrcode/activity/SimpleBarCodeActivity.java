package com.example.qrcode.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qrcode.R;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class SimpleBarCodeActivity extends AppCompatActivity {
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_simple);

        mImageView = findViewById(R.id.iv_barcode);
        showResultBarCode();
    }

    private void showResultBarCode() {
        Intent intent = getIntent();
        if (null != intent) {
            final String result = intent.getStringExtra("result");
            if (!TextUtils.isEmpty(result)) {
                new AsyncTask<Void, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Void... params) {
                        int width = BGAQRCodeUtil.dp2px(SimpleBarCodeActivity.this, 200);
                        int height = BGAQRCodeUtil.dp2px(SimpleBarCodeActivity.this, 90);
                        int textSize = BGAQRCodeUtil.sp2px(SimpleBarCodeActivity.this, 18);
                        return QRCodeEncoder.syncEncodeBarcode(result, width, height, textSize);
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        if (bitmap != null) {
                            mImageView.setImageBitmap(bitmap);
                        } else {
                            Toast.makeText(SimpleBarCodeActivity.this, "生成条底部带文字形码失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();
            }
        }
    }
}
