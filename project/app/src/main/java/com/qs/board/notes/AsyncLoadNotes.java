package com.qs.board.notes;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.qs.board.utils.NotesUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AsyncLoadNotes {

    public static void execute(WeakReference<Activity> weakContext, WeakReference<RecyclerView> weakRecyclerView) {

        new populateNotesList(weakContext, weakRecyclerView).execute();
    }

    private static class populateNotesList extends AsyncTask<Void, Void, Void> {

        //contacts
        private WeakReference<Activity> mWeakContext;
        private ArrayList<String> mTitles, mNotes, mDates, mPriorities;
        private WeakReference<RecyclerView> mWeakRecyclerView;


        private populateNotesList(WeakReference<Activity> weakContext, WeakReference<RecyclerView> weakRecyclerView) {
            mWeakContext = weakContext;
            mWeakRecyclerView = weakRecyclerView;
        }

        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {

            //for notes
            mTitles = NotesUtils.initNoteElement(mWeakContext.get(), NoteField.TITLES);

            //for notes
            mNotes = NotesUtils.initNoteElement(mWeakContext.get(), NoteField.NOTES);

            //for priorities
            mPriorities = NotesUtils.initNoteElement(mWeakContext.get(), NoteField.PRIORITIES);

            //for dates
            mDates = NotesUtils.initNoteElement(mWeakContext.get(), NoteField.DATES);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            //for notes
            NotesUtils.setupNotes(mWeakContext.get(), mTitles, mNotes, mPriorities, mDates, mWeakRecyclerView.get());
        }
    }
}
