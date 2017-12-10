package com.qs.board.utils;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qs.board.frequentcontacts.Contact;
import com.qs.board.frequentcontacts.ContactsRecyclerViewAdapter;

import java.util.ArrayList;

public class ContactsUtils {

    public static ArrayList<Contact> getAllContacts(Activity activity) {

        ArrayList<Contact> allContacts = new ArrayList<>();

        Cursor cursorStarred, cursorFrequents;
        Uri queryUri;

        String[] projection = new String[]{

                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.STARRED};

        //starred
        queryUri = ContactsContract.Contacts.CONTENT_URI;
        String starredSelection = ContactsContract.Contacts.STARRED + "='1'";

        cursorStarred = activity.getContentResolver().query(queryUri, projection, starredSelection, null, null);

        if (cursorStarred != null) {

            while (cursorStarred.moveToNext()) {

                String contactID = cursorStarred.getString(cursorStarred
                        .getColumnIndex(ContactsContract.Contacts._ID));

                String contactName = cursorStarred.getString(cursorStarred
                        .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                String contactThumbnail = cursorStarred.getString(cursorStarred
                        .getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));

                Contact contact = new Contact();

                contact.setContactId(contactID);
                contact.setContactName(contactName);

                contact.setContactThumbnail(contactThumbnail);

                allContacts.add(contact);

            }

            cursorStarred.close();
        }

        //frequents
        queryUri = ContactsContract.Contacts.CONTENT_FREQUENT_URI;
        String frequentSelection = ContactsContract.Contacts.STARRED + "='0'";

        cursorFrequents = activity.getContentResolver().query(queryUri, projection, frequentSelection, null, null);

        if (cursorFrequents != null) {

            while (cursorFrequents.moveToNext()) {

                String contactID = cursorFrequents.getString(cursorFrequents
                        .getColumnIndex(ContactsContract.Contacts._ID));

                String contactName = cursorFrequents.getString(cursorFrequents
                        .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                String contactThumbnail = cursorFrequents.getString(cursorFrequents
                        .getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));

                Contact contact = new Contact();

                contact.setContactId(contactID);
                contact.setContactName(contactName);

                contact.setContactThumbnail(contactThumbnail);

                allContacts.add(contact);

            }

            cursorFrequents.close();
        }

        return allContacts;
    }

    public static ArrayList<String> getNumbers(Activity activity, String contactId) {

        ArrayList<String> contactNumbers = new ArrayList<>();

        Uri queryUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor cursorMobile, cursorHome, cursorMain, cursorWork;

        // Using the contact ID now we will get contact phone number

        //get mobile numbers
        String typeMobile = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;

        cursorMobile = activity.getContentResolver().query(queryUri, projection, typeMobile, new String[]{contactId}, null);

        if (cursorMobile != null) {

            while (cursorMobile.moveToNext()) {

                String number = cursorMobile.getString(cursorMobile.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                contactNumbers.add(number);

            }
            cursorMobile.close();
        }

        //get home numbers
        String typeHome = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                ContactsContract.CommonDataKinds.Phone.TYPE_HOME;

        cursorHome = activity.getContentResolver().query(queryUri, projection, typeHome, new String[]{contactId}, null);

        if (cursorHome != null) {

            while (cursorHome.moveToNext()) {

                String number = cursorHome.getString(cursorHome.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                contactNumbers.add(number);

            }
            cursorHome.close();
        }

        //get main number
        String typeMain = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                ContactsContract.CommonDataKinds.Phone.TYPE_MAIN;

        cursorMain = activity.getContentResolver().query(queryUri, projection, typeMain, new String[]{contactId}, null);

        if (cursorMain != null) {

            while (cursorMain.moveToNext()) {

                String number = cursorMain.getString(cursorMain.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                contactNumbers.add(number);

            }
            cursorMain.close();
        }

        //get work number
        String typeWork = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                ContactsContract.CommonDataKinds.Phone.TYPE_WORK;


        // Using the contact ID now we will get contact phone number
        cursorWork = activity.getContentResolver().query(queryUri, projection, typeWork, new String[]{contactId}, null);

        if (cursorWork != null) {

            while (cursorWork.moveToNext()) {

                String number = cursorWork.getString(cursorWork.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                contactNumbers.add(number);

            }
            cursorWork.close();
        }

        //get other number
        String typeOther = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                ContactsContract.CommonDataKinds.Phone.TYPE_OTHER;


        // Using the contact ID now we will get contact phone number
        cursorWork = activity.getContentResolver().query(queryUri, projection, typeOther, new String[]{contactId}, null);

        if (cursorWork != null) {

            while (cursorWork.moveToNext()) {

                String number = cursorWork.getString(cursorWork.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                contactNumbers.add(number);

            }
            cursorWork.close();
        }
        return contactNumbers;
    }

    public static void setupContacts(final Activity activity, ArrayList<Contact> contacts, RecyclerView contactsRecyclerView) {

        contactsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        contactsRecyclerView.setLayoutManager(linearLayoutManager);

        ContactsRecyclerViewAdapter recyclerViewAdapter = new ContactsRecyclerViewAdapter(activity, contacts);

        contactsRecyclerView.setAdapter(recyclerViewAdapter);
    }
}
