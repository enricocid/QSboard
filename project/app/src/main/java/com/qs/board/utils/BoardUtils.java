package com.qs.board.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.qs.board.R;
import com.qs.board.preferences.PreferenceKeys;

public class BoardUtils {

    //retrieve board title
    public static String getBoardTitle(final Context context) {

        return PreferenceManager.getDefaultSharedPreferences(context).getString(PreferenceKeys.BOARD_TITLE_KEY, context.getString(R.string.app_name));
    }
}
