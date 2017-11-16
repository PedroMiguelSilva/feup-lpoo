package com.damon.merakiapp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.DataManager;

public class DialogDeleteAccountFragment extends DialogFragment {

    public static DialogDeleteAccountFragment newInstance(String title) {
        DialogDeleteAccountFragment frag = new DialogDeleteAccountFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_account_question)
                .setPositiveButton(R.string.option_sure, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteUser();
                        manageActivities();
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

    private void deleteUser() {
        DataManager.getInstance().getDataBase().deleteUser(DataManager.getInstance().getUserLogged());
        DataManager.getInstance().logout();
    }

    private void manageActivities() {
        getActivity().finish();
        Intent intent = new Intent(ViewManager.getInstance().getMainActivity(), LoginActivity.class);
        startActivity(intent);
        ViewManager.getInstance().getMainActivity().finish();
    }
}
