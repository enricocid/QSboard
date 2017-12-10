package com.qs.board;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;

import com.qs.board.preferences.SettingsActivity;
import com.qs.board.utils.ThemeUtils;

public class BoardActivity extends Activity {

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.options:
                Intent options = new Intent(this, SettingsActivity.class);
                startActivity(options);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.board_activity);

        ThemeUtils.applyTheme(new ContextThemeWrapper(this, getTheme()), this);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setBackgroundDrawable(new ColorDrawable(ThemeUtils.getColorAccent(this)));
        }

        getWindow().setStatusBarColor(ThemeUtils.getColorAccent(this));
    }
}
