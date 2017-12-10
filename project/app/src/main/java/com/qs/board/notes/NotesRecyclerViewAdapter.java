package com.qs.board.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qs.board.R;
import com.qs.board.preferences.ThemePreference;
import com.qs.board.utils.NotesUtils;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.SimpleViewHolder> {

    private static ArrayList<String> mNotesTitles, mNotesBodies, mNotesDates, mNotesPriorities;

    private Activity mActivity;

    //simple recycler view adapter with activity and string array as arguments
    public NotesRecyclerViewAdapter(Activity activity, ArrayList<String> titles, ArrayList<String> notes, ArrayList<String> priorities, ArrayList<String> dates) {
        mActivity = activity;
        mNotesTitles = titles;
        mNotesBodies = notes;
        mNotesDates = dates;
        mNotesPriorities = priorities;
    }

    static void youSureToShare(final Activity activity, final int pos) {

        final RecyclerView notesRecyclerView = activity.findViewById(R.id.notesRecyclerView);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);

        alertDialogBuilder.setTitle(activity.getString(R.string.u_sure));

        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();
                                notesRecyclerView.getAdapter().notifyDataSetChanged();

                            }
                        }
                )
                .setPositiveButton(activity.getString(R.string.share), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        notesRecyclerView.getAdapter().notifyDataSetChanged();
                        shareNote(activity, pos);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    static void copyToClipboard(Activity activity, int pos) {

        String body = mNotesBodies.get(pos);
        String date = mNotesDates.get(pos);
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("clipboard data ", body.substring(0, body.length() - date.length()));

        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }

        Toast.makeText(activity, activity.getString(R.string.copied), Toast.LENGTH_SHORT)
                .show();

    }

    private static void shareNote(Activity activity, int pos) {

        String body = mNotesBodies.get(pos);
        String date = mNotesDates.get(pos);

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, body.substring(0, body.length() - date.length()));
        activity.startActivity(Intent.createChooser(sharingIntent, activity.getString(R.string.share_with)));
    }

    static void youSureToDelete(final Activity activity, final int pos) {

        final RecyclerView notesRecyclerView = activity.findViewById(R.id.notesRecyclerView);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.DialogTheme));

        alertDialogBuilder.setTitle(activity.getString(R.string.u_sure));

        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();
                                notesRecyclerView.getAdapter().notifyDataSetChanged();

                            }
                        }
                )
                .setPositiveButton(activity.getString(R.string.delete), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        deleteNote(activity, pos);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    private static void deleteNote(Activity activity, int pos) {

        SQLiteDatabase notesDB = activity.openOrCreateDatabase("notesDB", MODE_PRIVATE, null);

        String title = NotesUtils.selectedItem(activity, NoteField.TITLES, pos, mNotesTitles);
        String note = NotesUtils.selectedItem(activity, NoteField.NOTES, pos, mNotesBodies);
        String date = NotesUtils.selectedItem(activity, NoteField.DATES, pos, mNotesDates);
        String priority = NotesUtils.selectedItem(activity, NoteField.PRIORITIES, pos, mNotesPriorities);


        String titlesTable, notesTable, datesTable, prioritiesTable,
                whereClause_title, whereClause_note, whereClause_date, whereClause_priority;

        String[] whereArgs_title, whereArgs_note, whereArgs_date, whereArgs_priority;

        //set the names of the tables
        titlesTable = "titles";
        notesTable = "notes";
        datesTable = "dates";
        prioritiesTable = "priorities";

        //set where clause
        whereClause_title = "title" + "=?";
        whereClause_note = "note" + "=?";
        whereClause_date = "date" + "=?";
        whereClause_priority = "priority" + "=?";

        //set the where arguments
        whereArgs_title = new String[]{title};
        whereArgs_note = new String[]{note};
        whereArgs_date = new String[]{date};
        whereArgs_priority = new String[]{priority};

        //delete 'em all
        notesDB.delete(titlesTable, whereClause_title, whereArgs_title);
        notesDB.delete(notesTable, whereClause_note, whereArgs_note);
        notesDB.delete(datesTable, whereClause_date, whereArgs_date);
        notesDB.delete(prioritiesTable, whereClause_priority, whereArgs_priority);

        //update data arrays and update the recycler view

        //remove all the adapter data
        mNotesTitles.remove(pos);
        mNotesBodies.remove(pos);
        mNotesDates.remove(pos);
        mNotesPriorities.remove(pos);

        NotesRecyclerViewAdapter notesRecyclerViewAdapter = new NotesRecyclerViewAdapter(activity, mNotesTitles, mNotesBodies, mNotesPriorities, mNotesDates);

        RecyclerView notesRecyclerView = activity.findViewById(R.id.notesRecyclerView);

        //and update the dynamic list
        //don't move this method above the db deletion method or you'll get javalangindexoutofboundsexception-invalid-index error
        notesRecyclerView.getAdapter().notifyDataSetChanged();

        notesRecyclerView.setAdapter(notesRecyclerViewAdapter);

    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflate recycler view items layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new SimpleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {

        String title = mNotesTitles.get(holder.getAdapterPosition());
        String note = mNotesBodies.get(holder.getAdapterPosition());
        String date = mNotesDates.get(holder.getAdapterPosition());
        String priority = mNotesPriorities.get(holder.getAdapterPosition());

        holder.title.setText(title.substring(0, title.length() - date.length()));
        holder.note.setText(note.substring(0, note.length() - date.length()));
        holder.date.setText(date);

        int priorityColor;

        if (mNotesPriorities.size() != 0) {

            priorityColor = ContextCompat.getColor(mActivity, Integer.valueOf(priority.substring(0, priority.length() - date.length())));
            ThemePreference.createCircularPreferenceBitmap(true, null, holder.priority, mActivity, priorityColor);
        }

        //show divider only if there are > 1 notes
        int visibility = mNotesPriorities.size() > 1 ? View.VISIBLE : View.GONE;

        holder.divider.setVisibility(visibility);

        holder.note.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //if we have a link, the long click on item will not open our beloved dialog
                openActionsDialog(holder.getAdapterPosition());
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {

        //get array length
        return mNotesBodies.size();
    }

    private void openActionsDialog(int pos) {

        Bundle bundle = new Bundle();

        bundle.putInt("pos", pos);

        FragmentManager fm = mActivity.getFragmentManager();
        DoSomethingDialog dialogFragment = new DoSomethingDialog();

        dialogFragment.setArguments(bundle);
        dialogFragment.show(fm, "dosomethingwithme");
    }

    //simple view holder implementing click and long click listeners and with activity and itemView as arguments
    class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private ImageView priority;
        private TextView title, note, date;
        private View divider;

        SimpleViewHolder(View itemView) {
            super(itemView);

            this.divider = itemView.findViewById(R.id.divider);
            this.title = itemView.findViewById(R.id.title);
            this.note = itemView.findViewById(R.id.note);
            this.date = itemView.findViewById(R.id.date);
            this.priority = itemView.findViewById(R.id.priority);

            itemView.setOnLongClickListener(this);

        }

        @Override
        public boolean onLongClick(View v) {

            openActionsDialog(getAdapterPosition());
            return false;
        }
    }
}