package com.qs.board;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.qs.board.frequentcontacts.AsyncLoadContacts;
import com.qs.board.notes.AddNoteDialog;
import com.qs.board.notes.AsyncLoadNotes;
import com.qs.board.preferences.SeekBarPreference;
import com.qs.board.preferences.SettingsActivity;
import com.qs.board.utils.BoardUtils;
import com.qs.board.utils.ThemeUtils;

import java.lang.ref.WeakReference;

public class BoardActivity extends Activity {

    private WeakReference<Activity> mWeakContext;

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
            ActionBar actionBar = getActionBar();

            int accent = ThemeUtils.getColorAccent(this);

            int color = Color.argb(SeekBarPreference.getAlphaValue(this), Color.red(accent), Color.green(accent), Color.blue(accent));

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(color));
            actionBar.setTitle(BoardUtils.getBoardTitle(this));
            getWindow().setStatusBarColor(color);
        }

        int color = ContextCompat.getColor(this, R.color.black);

        int black = Color.argb(SeekBarPreference.getAlphaValue(this), Color.red(color), Color.green(color), Color.blue(color));

        getWindow().setBackgroundDrawable(new ColorDrawable(black));

        mWeakContext = new WeakReference<Activity>(this);

        initBoard();

        initNotes();

        initContacts();
    }

    private void initBoard() {

        ImageButton mNotesAdd = findViewById(R.id.add);

        mNotesAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });
    }

    //method to add note inside the db and the dynamic ListView
    private void addNote() {

        AddNoteDialog.show(this, true);
    }

    private void initNotes() {

        RecyclerView notesRecyclerView = findViewById(R.id.notesRecyclerView);

        AsyncLoadNotes.execute(mWeakContext, new WeakReference<>(notesRecyclerView));
    }

    private void initContacts() {

        RecyclerView contactsRecyclerView = findViewById(R.id.contactsRecyclerView);

        AsyncLoadContacts.execute(mWeakContext, new WeakReference<>(contactsRecyclerView));
    }

    @Override
    protected void onPause() {

        AddNoteDialog.show(this, false);

        super.onPause();
    }
}
