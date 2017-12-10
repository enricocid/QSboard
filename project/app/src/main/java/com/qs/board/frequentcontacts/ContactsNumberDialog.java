package com.qs.board.frequentcontacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qs.board.R;

import java.util.ArrayList;

public class ContactsNumberDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ArrayList<String> contactPhones = getArguments().getStringArrayList("contactPhones");

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        final ViewGroup nullParent = null;

        View dialogView = layoutInflater.inflate(R.layout.contact_phones_layout, nullParent, false);

        RecyclerView phones_rv = dialogView.findViewById(R.id.phones_rv);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity());

        phones_rv.setHasFixedSize(true);
        phones_rv.setLayoutManager(layoutManager);

        //set the recycler view adapter and pass arguments to the adapter to it
        phones_rv.setAdapter(new PhonesRecyclerViewAdapter(getActivity(), contactPhones));

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.DialogTheme));

        builder.setView(dialogView);

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }
}