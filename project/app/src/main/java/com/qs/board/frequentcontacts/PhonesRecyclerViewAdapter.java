package com.qs.board.frequentcontacts;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qs.board.R;
import com.qs.board.utils.CallUtil;

import java.util.ArrayList;

class PhonesRecyclerViewAdapter extends RecyclerView.Adapter<PhonesRecyclerViewAdapter.SimpleViewHolder> {

    private ArrayList<String> mPhones;
    private Activity mActivity;

    //simple recycler view adapter with activity and array list contact as arguments
    PhonesRecyclerViewAdapter(Activity activity, ArrayList<String> phones) {
        mPhones = phones;
        mActivity = activity;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflate recycler view items layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_phone_item, parent, false);

        return new SimpleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {

        holder.phone.setText(mPhones.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {

        //get array length
        return mPhones.size();
    }

    //simple view holder implementing click and long click listeners and with activity and itemView as arguments
    class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView phone;

        SimpleViewHolder(View itemView) {
            super(itemView);

            phone = itemView.findViewById(R.id.phone);

            //enable click and on long click
            itemView.setOnClickListener(this);
        }

        //add click
        @Override
        public void onClick(View v) {

            CallUtil.performCall(mActivity, mPhones.get(getAdapterPosition()));
        }
    }
}