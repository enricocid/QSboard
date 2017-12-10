package com.qs.board.frequentcontacts;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.qs.board.utils.CallUtil;
import com.qs.board.utils.ContactsUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

class AsyncLoadContactPhones {

    static void execute(WeakReference<Activity> weakContext, String contactId) {

        new populateContactPhonesList(weakContext, contactId).execute();
    }

    private static class populateContactPhonesList extends AsyncTask<Void, Void, Void> {

        ArrayList<String> mPhonesList;
        private WeakReference<Activity> mWeakContext;
        private String mContactId;

        private populateContactPhonesList(WeakReference<Activity> weakContext, String contactId) {
            mWeakContext = weakContext;
            mContactId = contactId;
        }

        @Override
        protected Void doInBackground(Void... params) {

            mPhonesList = ContactsUtils.getNumbers(mWeakContext.get(), mContactId);

            if (mPhonesList.isEmpty()) {
                cancel(true);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (mPhonesList.size() > 1) {

                Bundle bundle = new Bundle();

                FragmentManager fm = mWeakContext.get().getFragmentManager();

                bundle.putStringArrayList("contactPhones", mPhonesList);

                ContactsNumberDialog dialogFragment = new ContactsNumberDialog();

                dialogFragment.setArguments(bundle);

                dialogFragment.show(fm, "callMe");

            } else {

                CallUtil.performCall(mWeakContext.get(), mPhonesList.get(0));
            }
        }
    }
}
