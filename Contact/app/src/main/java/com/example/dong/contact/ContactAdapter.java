package com.example.dong.contact;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolderContact>{

    private List<Contact> mContacts;
    private Context mContext;

    public ContactAdapter(List<Contact> contacts, Context context){
        this.mContacts = contacts;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolderContact onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_contact, viewGroup, false);
        return new ViewHolderContact(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderContact viewHolderContact, int position) {
        Contact contact = mContacts.get(position);
        viewHolderContact.mTextName.setText(contact.getName());
        viewHolderContact.mTextPhone.setText(contact.getPhone());
    }

    @Override
    public int getItemCount() {
        return mContacts == null ? 0 : mContacts.size();
    }

    public class ViewHolderContact extends RecyclerView.ViewHolder {

        private TextView mTextName;
        private TextView mTextPhone;

        public ViewHolderContact(@NonNull View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.textName);
            mTextPhone = itemView.findViewById(R.id.textPhone);
        }
    }

}
