package com.qs.board;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);

        managePermissions();
    }

    private void startSettings() {

        Toast.makeText(this, "thankyou:)", Toast.LENGTH_SHORT)
                .show();

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SettingsFragment())
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case PermissionUtils.CONTACT_REQUEST_CODE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    PermissionUtils.requestCallPermission(this);
                }
            }
            break;

            case PermissionUtils.CALL_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startSettings();
                }
                break;
        }
    }

    private void managePermissions() {

        if (PermissionUtils.checkPermission(this, PermissionUtils.READ_CONTACTS) && PermissionUtils.checkPermission(this, PermissionUtils.CALL_PHONE)) {

            PermissionUtils.requestContactPermission(this);
        }
    }

    /**
     * This fragment shows the preferences.
     */
    public static class SettingsFragment extends PreferenceFragment {

        private SharedPreferences.OnSharedPreferenceChangeListener mListenerOptions;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.pref_general);

            mListenerOptions = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                    switch (key) {

                        //case SOME_KEY:
                        //do something
                        //break;
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
}