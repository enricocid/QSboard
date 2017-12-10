package com.qs.board.frequentcontacts;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.qs.board.utils.ContactsUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AsyncLoadContacts {

    public static void execute(WeakReference<Activity> weakContext, WeakReference<RecyclerView> weakRecyclerView) {

        new populateContactsList(weakContext, weakRecyclerView).execute();
    }

    private static class populateContactsList extends AsyncTask<Void, Void, Void> {

        //contacts
        private WeakReference<Activity> mWeakContext;
        private ArrayList<Contact> mContactsList;
        private WeakReference<RecyclerView> mWeakRecyclerView;

        private populateContactsList(WeakReference<Activity> weakContext, WeakReference<RecyclerView> weakRecyclerView) {
            mWeakContext = weakContext;
            mWeakRecyclerView = weakRecyclerView;
        }

        @Override
        protected Void doInBackground(Void... params) {

            //for contacts
            mContactsList = ContactsUtils.getAllContacts(mWeakContext.get());

            Collections.sort(mContactsList, new Comparator<Contact>() {
                public int compare(Contact obj1, Contact obj2) {
                    // ## Ascending order
                    return obj1.getContactName().compareToIgnoreCase(obj2.getContactName()); // To compare string values
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            //for contacts
            ContactsUtils.setupContacts(mWeakContext.get(), mContactsList, mWeakRecyclerView.get());
        }
    }
}
