package com.damon.merakiapp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.DataManager;
import com.damon.merakiapp.logic.Task;

import java.util.ArrayList;

public class DialogCleanArchiveFragment extends DialogFragment {

    public static DialogCleanArchiveFragment newInstance(String title) {
        DialogCleanArchiveFragment frag = new DialogCleanArchiveFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.clean_completed_question)
                .setPositiveButton(R.string.option_sure, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteArchive();
                        ViewManager.getInstance().updateInformation();
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.option_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }

    private void deleteArchive() {
        int userId = DataManager.getInstance().getUserLogged().getId();
        ArrayList<Task> archivedTasks = DataManager.getInstance().getDataBase().getUserTasks(userId, true);

        if (archivedTasks != null)
            for (int i = 0; i < archivedTasks.size(); i++) {
                DataManager.getInstance().getDataBase().deleteTask(archivedTasks.get(i));
            }

    }

}
