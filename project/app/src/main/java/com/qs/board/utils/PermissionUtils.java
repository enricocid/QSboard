package com.qs.board.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

public class PermissionUtils {

    public static final int CONTACT_REQUEST_CODE = 1;
    public static final int CALL_REQUEST_CODE = 2;

    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;

    public static void requestCallPermission(Activity activity) {
        activity.requestPermissions(new String[]{CALL_PHONE}
                , CALL_REQUEST_CODE);
    }

    public static void requestContactPermission(Activity activity) {

        activity.requestPermissions(new String[]{READ_CONTACTS}
                , CONTACT_REQUEST_CODE);
    }

    public static boolean checkPermission(Activity activity, String code) {
        return activity.checkSelfPermission(code) == PackageManager.PERMISSION_GRANTED;
    }
}
