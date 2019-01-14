package com.example.qrcode.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qrcode.R;
import com.example.qrcode.utils.Logcat;

import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanGoodsBarCodeActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private static final String TAG = ScanGoodsBarCodeActivity.class.getSimpleName();

    private ZXingView mZXingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_goods);

        mZXingView = findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startCamera();// 打开后置摄像头开始预览，但并未开始识别
        // mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);// 打开前置摄像头开始预览，但并未开始识别
        mZXingView.startSpotAndShowRect();// 显示扫描框，并开始识别
        change2ScanBarCode();
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

    /**
     * 切换为只扫描条形码
     */
    private void change2ScanBarCode() {
        mZXingView.changeToScanBarcodeStyle();// 切换成扫描条码样式
        mZXingView.setType(BarcodeType.ONE_DIMENSION, null);// 只识别一维条码
        mZXingView.getScanBoxView().setOnlyDecodeScanBoxArea(true); // 仅识别扫描框中的码
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Logcat.d(TAG, "扫描结果为：" + result);
        Intent intent = new Intent(this, GoodsInfoActivity.class);
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
