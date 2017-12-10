package com.qs.board.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;

import com.qs.board.R;
import com.qs.board.utils.BoardUtils;

/**
 * This fragment shows the preferences.
 */
public class SettingsFragment extends PreferenceFragment {

    private SharedPreferences.OnSharedPreferenceChangeListener mListenerOptions;
    private EditTextPreference mTitlePref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_general);

        mTitlePref = (EditTextPreference) findPreference(PreferenceKeys.BOARD_TITLE_KEY);

        mTitlePref.setSummary(BoardUtils.getBoardTitle(getActivity()));

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
}