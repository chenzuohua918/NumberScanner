package com.example.qrcode.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.qrcode.R;
import com.example.qrcode.base.BaseActivity;
import com.example.qrcode.utils.PermissionUtils;

public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onViewCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);// 不显示左侧返回图标按钮
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkCameraPermission();
    }

    /**
     * 检测相机权限
     */
    private boolean checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                PermissionUtils.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PermissionUtils.REQUEST_CODE_CAMERA);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtils.REQUEST_CODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                break;
            default:
                break;
        }
    }

    public void scanBarCode(View view) {
        if (checkCameraPermission()) {
            startActivity(new Intent(this, ScanBarCodeActivity.class));
        }
    }

    public void scanQRCode(View view) {
        if (checkCameraPermission()) {
            startActivity(new Intent(this, ScanQRCodeActivity.class));
        }
    }

    public void scanExpress(View view) {
        if (checkCameraPermission()) {
            startActivity(new Intent(this, ScanExpressActivity.class));
        }
    }

    public void scanGoods(View view) {
        if (checkCameraPermission()) {
            startActivity(new Intent(this, ScanGoodsBarCodeActivity.class));
        }
    }
}
