package com.example.dong.contact;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.dong.contact.Constants.REQUEST_CODE;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerContact;
    private ContactAdapter mContactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        checkPermission();
    }

    private void initComponent() {
        mRecyclerContact = findViewById(R.id.recyclerContact);
    }

    private void setComponent() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        mRecyclerContact.setLayoutManager(manager);
        new TaskLoadContact(this, mLoadcontacts).execute();
    }

    private LoadContacts mLoadcontacts = new LoadContacts() {
        @Override
        public void loadContact(List<Contact> contacts) {
            mContactAdapter = new ContactAdapter(contacts, getBaseContext());
            mRecyclerContact.setAdapter(mContactAdapter);
            mContactAdapter.notifyDataSetChanged();
        }
    };

    public void checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                    showDialog(Constants.MESSAGE, this, Manifest.permission.READ_CONTACTS);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, Constants.REQUEST_CODE);
                }
            } else {
                setComponent();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setComponent();
                } else {
                    Toast.makeText(this, Constants.MESSAGE, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    public void showDialog(final String msg, final Context context, final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(Constants.TITLE_DIALOG);
        alertBuilder.setMessage(msg + Constants.MESSAGE_DIALOG);
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                REQUEST_CODE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

}
