package com.example.dong.contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class TaskLoadContact extends AsyncTask<Void, Void, List<Contact>> {

    private Context mContext;
    private LoadContacts mLoadContacts;
    private ContentResolver mResolver;
    private Cursor mCursor;

    public TaskLoadContact(Context context, LoadContacts loadContacts) {
        this.mContext = context;
        this.mLoadContacts = loadContacts;
    }


    @Override
    protected List<Contact> doInBackground(Void... voids) {
        List<Contact> contacts = new ArrayList<>();
        mResolver = mContext.getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        mCursor = mResolver.query(uri, null, null, null, null, null);
        if (mCursor != null && mCursor.getCount() > 0) {
            for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
                int columnIndexName = mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int columnIndexPhone = mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                contacts.add(new Contact(mCursor.getString(columnIndexName), mCursor.getString(columnIndexPhone)));
            }
        }
        return contacts;
    }

    @Override
    protected void onPostExecute(List<Contact> contacts) {
        super.onPostExecute(contacts);
        mLoadContacts.loadContact(contacts);
    }

}
