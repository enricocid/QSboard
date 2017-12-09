package com.qs.board;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

class PermissionUtils {

    static final int CONTACT_REQUEST_CODE = 1;
    static final int CALL_REQUEST_CODE = 2;

    static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    static final String CALL_PHONE = Manifest.permission.CALL_PHONE;

    //Manifest.permission.CALL_PHONE
    static void requestCallPermission(Activity activity) {
        activity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}
                , CALL_REQUEST_CODE);
    }

    static void requestContactPermission(Activity activity) {

        activity.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}
                , CONTACT_REQUEST_CODE);
    }

    static boolean checkPermission(Activity activity, String code) {
        return activity.checkSelfPermission(code) != PackageManager.PERMISSION_GRANTED;
    }
}
