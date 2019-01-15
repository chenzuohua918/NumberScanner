package com.example.qrcode.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.qrcode.R;
import com.example.qrcode.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CAMERA = 1;

    private PermissionUtils mPermissionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPermissionUtils = PermissionUtils.getInstance();
        mPermissionUtils.init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mPermissionUtils.checkSelfPermission(Manifest.permission.CAMERA)) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                break;
            default:
                break;
        }
    }

    public void scanBarCode(View view) {
        startActivity(new Intent(this, ScanBarCodeActivity.class));
    }

    public void scanQRCode(View view) {
        startActivity(new Intent(this, ScanQRCodeActivity.class));
    }

    public void scanExpress(View view) {
        startActivity(new Intent(this, ScanExpressActivity.class));
    }

    public void scanGoods(View view) {
        startActivity(new Intent(this, ScanGoodsBarCodeActivity.class));
    }
}
