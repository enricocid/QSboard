package com.qs.board.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.qs.board.BoardActivity;
import com.qs.board.R;
import com.qs.board.utils.BoardUtils;
import com.qs.board.utils.NotificationUtils;

public class SettingsFragment extends PreferenceFragment {

    private SharedPreferences.OnSharedPreferenceChangeListener mListenerOptions;
    private EditTextPreference mTitlePref;
    private String boardTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_general);

        mTitlePref = (EditTextPreference) findPreference(PreferenceKeys.BOARD_TITLE_KEY);

        boardTitle = BoardUtils.getBoardTitle(getActivity());

        mTitlePref.setSummary(boardTitle);

        Preference addNotification = findPreference(PreferenceKeys.NOTIFICATION_KEY);
        addNotification.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                NotificationUtils.addNotification(getActivity());
                return false;
            }
        });

        Preference addShortcut = findPreference(PreferenceKeys.SHORTCUT_KEY);
        addShortcut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                addShortcut();
                return false;
            }
        });

        mListenerOptions = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                switch (key) {

                    case PreferenceKeys.CHOOSE_ACCENT_KEY:
                        getActivity().recreate();
                        break;
                    case PreferenceKeys.BOARD_TITLE_KEY:
                        mTitlePref.setSummary(mTitlePref.getText());
                        break;
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(mListenerOptions);
    }

    //unregister preferences changes
    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(mListenerOptions);
        super.onPause();
    }

    //add shortcut to home
    private void addShortcut() {

        //Adding shortcut for MainActivity
        //on Home screen
        Intent shortcutIntent = new Intent(getActivity(),
                BoardActivity.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent addIntent = new Intent();
        addIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, boardTitle);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getActivity(),
                        R.drawable.ic_add_circle));

        addIntent
                .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        addIntent.putExtra("duplicate", false);  //may it's already there so don't duplicate
        getActivity().sendBroadcast(addIntent);
    }
}