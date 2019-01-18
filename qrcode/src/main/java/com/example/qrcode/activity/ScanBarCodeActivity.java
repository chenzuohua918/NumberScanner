package com.example.qrcode.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.qrcode.R;
import com.example.qrcode.base.BaseActivity;
import com.example.qrcode.utils.Logcat;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanBarCodeActivity extends BaseActivity implements QRCodeView.Delegate {
    private static final String TAG = ScanBarCodeActivity.class.getSimpleName();

    private ZXingView mZXingView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_barcode;
    }

    @Override
    public void onViewCreated(Bundle savedInstanceState) {
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
        Intent intent = new Intent(this, ScanBarCodeResultActivity.class);
        intent.putExtra("result", result);
        startActivity(intent);
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
