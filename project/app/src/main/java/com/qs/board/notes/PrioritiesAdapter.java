package com.qs.board.notes;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qs.board.R;
import com.qs.board.preferences.ThemePreference;

class PrioritiesAdapter extends RecyclerView.Adapter<PrioritiesAdapter.SimpleViewHolder> {

    private static int mSelectedPriority;
    private Integer[] mPriorities;
    private Activity mActivity;
    private TextView mPriority;

    //simple recycler view adapter with activity and integer array as arguments
    PrioritiesAdapter(Activity activity, TextView priority, Integer[] priorities) {
        mPriorities = priorities;
        mActivity = activity;
        mPriority = priority;
    }

    static String priority() {

        return String.valueOf(mSelectedPriority);
    }

    private static void setDefaultPriority(int color) {

        mSelectedPriority = color;

    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflate recycler view items layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.priority_item, parent, false);

        return new SimpleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {

        ThemePreference.createCircularPreferenceBitmap(true, null, holder.priorityItem, mActivity, ContextCompat.getColor(mActivity, mPriorities[holder.getAdapterPosition()]));

        //set default priority to neutral
        setDefaultPriority(mPriorities[0]);
        mPriority.setTextColor(ContextCompat.getColor(mActivity, mSelectedPriority));

    }

    @Override
    public int getItemCount() {

        //get array length
        return mPriorities.length;
    }

    //simple view holder implementing click listener and with activity and itemView as arguments
    class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView priorityItem;

        SimpleViewHolder(View itemView) {
            super(itemView);

            //get the views here
            priorityItem = itemView.findViewById(R.id.color);

            //enable click and on long click
            itemView.setOnClickListener(this);
        }

        //add click
        @Override
        public void onClick(View v) {

            mSelectedPriority = mPriorities[getAdapterPosition()];

            mPriority.setTextColor(ContextCompat.getColor(mActivity, mSelectedPriority));
        }
    }
}