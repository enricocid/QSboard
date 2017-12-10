package com.qs.board.preferences;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Toast;

import com.qs.board.R;
import com.qs.board.utils.PermissionUtils;

public class SettingsActivity extends Activity {

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);

        mActionBar = getActionBar();

        managePermissions();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void startSettings() {

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SettingsFragment())
                .commit();

        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

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
                    Toast.makeText(this, getString(R.string.thanks), Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }

    private void managePermissions() {

        if (PermissionUtils.checkPermission(this, PermissionUtils.READ_CONTACTS) && PermissionUtils.checkPermission(this, PermissionUtils.CALL_PHONE)) {

            PermissionUtils.requestContactPermission(this);
        } else {
            startSettings();
        }
    }
}