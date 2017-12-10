package com.qs.board.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;

import com.qs.board.R;
import com.qs.board.preferences.PreferenceKeys;

public class ThemeUtils {

    //method to apply selected theme
    public static void applyTheme(ContextThemeWrapper contextThemeWrapper, Context context) {
        int theme = resolveTheme(context);
        contextThemeWrapper.setTheme(theme);
    }

    public static int getColorAccent(Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.colorAccent, value, true);
        return value.data;
    }

    public static int resolveColor(Context context) {

        String choice = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PreferenceKeys.CHOOSE_ACCENT_KEY, String.valueOf(0));

        switch (Integer.parseInt(choice)) {
            default:
            case 0:
                return R.color.accent;

            case 1:
                return R.color.material_red_400;

            case 2:
                return R.color.material_pink_400;

            case 3:
                return R.color.material_purple_400;

            case 4:
                return R.color.material_deepPurple_400;

            case 5:
                return R.color.material_indigo_400;

            case 6:
                return R.color.material_blue_400;

            case 7:
                return R.color.material_lightBlue_400;

            case 8:
                return R.color.material_cyan_400;

            case 9:
                return R.color.material_teal_400;

            case 10:
                return R.color.material_green_400;

            case 11:
                return R.color.material_amber_400;

            case 12:
                return R.color.material_orange_400;

            case 13:
                return R.color.material_deepOrange_400;

            case 14:
                return R.color.material_brown_400;

            case 15:
                return R.color.material_blueGrey_400;

        }
    }

    //multi-preference dialog for theme options
    private static int resolveTheme(Context context) {

        String choice = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PreferenceKeys.CHOOSE_ACCENT_KEY, String.valueOf(0));

        switch (Integer.parseInt(choice)) {
            default:
            case 0:
                return R.style.Base_Theme;

            case 1:
                return R.style.Base_Theme_Red;

            case 2:
                return R.style.Base_Theme_Pink;

            case 3:
                return R.style.Base_Theme_Purple;

            case 4:
                return R.style.Base_Theme_DeepPurple;

            case 5:
                return R.style.Base_Theme_Indigo;

            case 6:
                return R.style.Base_Theme_Blue;

            case 7:
                return R.style.Base_Theme_LightBlue;

            case 8:
                return R.style.Base_Theme_Cyan;

            case 9:
                return R.style.Base_Theme_Teal;

            case 10:
                return R.style.Base_Theme_Green;

            case 11:
                return R.style.Base_Theme_Amber;

            case 12:
                return R.style.Base_Theme_Orange;

            case 13:
                return R.style.Base_Theme_DeepOrange;

            case 14:
                return R.style.Base_Theme_Brown;

            case 15:
                return R.style.Base_Theme_BlueGrey;

        }
    }
}
