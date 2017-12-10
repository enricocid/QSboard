package com.qs.board.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.qs.board.R;
import com.qs.board.preferences.PreferenceKeys;

/**
 * Created by Enrico on 25/09/2017.
 */

public class BoardUtils {

    //retrieve board title
    public static String getBoardTitle(final Context context) {

        return PreferenceManager.getDefaultSharedPreferences(context).getString(PreferenceKeys.BOARD_TITLE_KEY, context.getString(R.string.app_name));
    }
}
