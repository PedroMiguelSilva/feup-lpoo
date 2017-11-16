package com.damon.merakiapp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.DataManager;

public class DialogContentFragment extends DialogFragment {

    private String stats;
    private int numTasks;
    private int numArchivedTasks;
    private int numCategories;

    public static DialogContentFragment newInstance(String title) {
        DialogContentFragment frag = new DialogContentFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_content);
        extractStats();
        builder.setMessage(this.stats)
                .setPositiveButton(R.string.option_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }

    private void extractStats() {
        extractNumTasks();
        extractNumArchivedTasks();
        extractNumCategories();

        this.stats = "Tasks: " + this.numTasks + "\n";
        this.stats += "Categories: " + this.numCategories + "\n";
        this.stats += "Archived tasks: " + this.numArchivedTasks;
    }

    private void extractNumTasks() {
        int userId = DataManager.getInstance().getUserLogged().getId();

        if (DataManager.getInstance().getDataBase().getUserTasks(userId, false) != null)
            this.numTasks = DataManager.getInstance().getDataBase().getUserTasks(userId, false).size();
        else this.numTasks = 0;
    }

    private void extractNumArchivedTasks() {
        int userId = DataManager.getInstance().getUserLogged().getId();

        if (DataManager.getInstance().getDataBase().getUserTasks(userId, true) != null)
            this.numArchivedTasks = DataManager.getInstance().getDataBase().getUserTasks(userId, true).size();
        else this.numArchivedTasks = 0;
    }

    private void extractNumCategories() {
        int userId = DataManager.getInstance().getUserLogged().getId();

        if (DataManager.getInstance().getDataBase().getUserCategories(userId) != null)
            this.numCategories = DataManager.getInstance().getDataBase().getUserCategories(userId).size();
        else this.numCategories = 0;
    }
}
