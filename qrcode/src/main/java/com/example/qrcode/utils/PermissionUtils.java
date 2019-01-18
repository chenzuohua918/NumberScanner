package com.example.qrcode.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

public class PermissionUtils {
    public static final int REQUEST_CODE_CAMERA = 1;

    public static boolean checkSelfPermission(Activity activity, String permission) {
        return activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(Activity activity, String[] permission, int requestCode) {
        activity.requestPermissions(permission, requestCode);
    }
}
