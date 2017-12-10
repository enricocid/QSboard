package com.qs.board.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class CallUtil {

    public static void performCall(Activity activity, String number) {

        if (PermissionUtils.checkPermission(activity, PermissionUtils.CALL_PHONE)) {

            activity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}
                    , PermissionUtils.CALL_REQUEST_CODE);
        } else {

            call(activity, number);
        }
    }

    private static void call(Activity activity, String number) {

        try {
            Uri call = Uri.parse("tel:" + number);
            Intent surf = new Intent(Intent.ACTION_CALL, call);
            activity.startActivity(surf);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
