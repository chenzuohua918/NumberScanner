package com.example.qrcode.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.qrcode.R;
import com.example.qrcode.utils.Logcat;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanQRCodeActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private static final String TAG = ScanQRCodeActivity.class.getSimpleName();

    private ZXingView mZXingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        mZXingView = findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startCamera();// 打开后置摄像头开始预览，但并未开始识别
        // mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);// 打开前置摄像头开始预览，但并未开始识别
        mZXingView.startSpotAndShowRect();// 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera();// 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy();// 销毁二维码扫描控件
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Logcat.d(TAG, "扫描结果为：" + result);
        if (!TextUtils.isEmpty(result)) {
            if (result.startsWith("http://") || result.startsWith("https://")
                    || result.startsWith("HTTP://") || result.startsWith("HTTPS://")) {
                // 浏览器打开
                Uri uri = Uri.parse(result);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, ScanQRCodeResultActivity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {// 摄像头光线变化
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = mZXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZXingView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Logcat.e(TAG, "打开相机出错！");
    }
}
