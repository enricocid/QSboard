package com.qs.board;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;

public class BoardActivity extends Activity {

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
