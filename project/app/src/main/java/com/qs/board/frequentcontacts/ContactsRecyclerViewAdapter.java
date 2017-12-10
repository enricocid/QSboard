package com.qs.board.frequentcontacts;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qs.board.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.SimpleViewHolder> {

    private ArrayList<Contact> mContacts;
    private Activity mActivity;

    //simple recycler view adapter with activity and array list contact as arguments
    public ContactsRecyclerViewAdapter(Activity activity, ArrayList<Contact> contacts) {
        mContacts = contacts;
        mActivity = activity;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflate recycler view items layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);

        return new SimpleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {

        holder.name.setText(mContacts.get(position).getContactName());

        if (mContacts.get(holder.getAdapterPosition()).getContactThumbnail() != null) {
            holder.thumbnail.setImageDrawable(RoundedContact.get(mActivity, Uri.parse(mContacts.get(holder.getAdapterPosition()).getContactThumbnail())));
        } else {
            holder.thumbnail.setImageBitmap(RoundedContact.createRoundIconWithText(mActivity, String.valueOf(mContacts.get(position).getContactName().charAt(0))));
        }
    }

    @Override
    public int getItemCount() {

        //get array length
        return mContacts.size();
    }

    //simple view holder implementing click and long click listeners and with activity and itemView as arguments
    class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;

        ImageView thumbnail;

        SimpleViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);

            thumbnail = itemView.findViewById(R.id.thumbnail);

            //enable click and on long click
            itemView.setOnClickListener(this);
        }

        //add click
        @Override
        public void onClick(View v) {

            AsyncLoadContactPhones.execute(new WeakReference<>(mActivity), mContacts.get(getAdapterPosition()).getContactId());
        }
    }
}
