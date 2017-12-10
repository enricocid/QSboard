package com.qs.board;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qs.board.frequentcontacts.AsyncLoadContacts;
import com.qs.board.notes.AddNoteDialog;
import com.qs.board.notes.AsyncLoadNotes;
import com.qs.board.preferences.SeekBarPreference;
import com.qs.board.preferences.SettingsActivity;
import com.qs.board.utils.BoardUtils;
import com.qs.board.utils.ImmersiveUtils;
import com.qs.board.utils.ThemeUtils;

import java.lang.ref.WeakReference;

public class BoardActivity extends Activity {

    private WeakReference<Activity> mWeakContext;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (hasFocus && ImmersiveUtils.isImmersiveMode(this)) {
            ImmersiveUtils.toggleHideyBar(this, true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.board_activity);

        ThemeUtils.applyTheme(new ContextThemeWrapper(this, getTheme()), this);

        int accent = ThemeUtils.getColorAccent(this);

        int alphaAccent = Color.argb(SeekBarPreference.getAlphaValue(this), Color.red(accent), Color.green(accent), Color.blue(accent));
        getWindow().setStatusBarColor(alphaAccent);

        View boardToolbar = findViewById(R.id.board_bar);
        boardToolbar.setBackgroundColor(alphaAccent);

        int bgColor = ContextCompat.getColor(this, R.color.black);

        int black = Color.argb(SeekBarPreference.getAlphaValue(this), Color.red(bgColor), Color.green(bgColor), Color.blue(bgColor));

        getWindow().setBackgroundDrawable(new ColorDrawable(black));

        mWeakContext = new WeakReference<Activity>(this);

        initBoard();

        initNotes();

        initContacts();

        if (ImmersiveUtils.isImmersiveMode(this)) {
            ImmersiveUtils.toggleHideyBar(this, false);
        }
    }

    private void initBoard() {

        ImageButton mNotesAdd = findViewById(R.id.add);

        mNotesAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });

        ImageButton boardCancel = findViewById(R.id.back);

        boardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageButton boardEdit = findViewById(R.id.edit);

        boardEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent options = new Intent(BoardActivity.this, SettingsActivity.class);
                startActivity(options);
            }
        });

        TextView boardTitle = findViewById(R.id.board_title);

        boardTitle.setText(BoardUtils.getBoardTitle(this));
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
