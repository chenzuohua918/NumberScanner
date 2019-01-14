package com.example.qrcode.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

public class PermissionUtils {
    private static PermissionUtils instance;

    private Activity mActivity;

    private PermissionUtils() {}

    private static synchronized void syncInit() {
        if (null == instance) {
            instance = new PermissionUtils();
        }
    }

    public static PermissionUtils getInstance() {
        if (null == instance) {
            syncInit();
        }
        return instance;
    }

    public void init(Activity activity) {
        this.mActivity = activity;
    }

    public boolean checkSelfPermission(String permission) {
        return mActivity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
    }
}
