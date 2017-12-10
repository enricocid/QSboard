package com.qs.board.utils;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.view.View;

import com.qs.board.preferences.PreferenceKeys;

public class ImmersiveUtils {

    public static void toggleHideyBar(Activity activity, boolean onResume) {

        int newUiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();

        if (onResume) {

            applyImmersiveMode(activity);

        } else {
            newUiOptions ^= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            newUiOptions ^= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            newUiOptions ^= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE;
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            activity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        }
    }

    public static boolean isImmersiveMode(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PreferenceKeys.IMMERSIVE_KEY, true);
    }

    //if yes apply immersive mode
    private static void applyImmersiveMode(Activity activity) {

        //immersive mode
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE

                        //Sticky flag - This is the UI you see if you use the IMMERSIVE_STICKY flag, and the user
                        //swipes to display the system bars. Semi-transparent bars temporarily appear
                        //and then hide again
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
