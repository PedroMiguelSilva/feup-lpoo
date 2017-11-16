package com.damon.merakiapp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.DataManager;

public class DialogEditEmailFragment extends DialogFragment {

    private EditText userInput;

    public static DialogEditEmailFragment newInstance(String title) {
        DialogEditEmailFragment frag = new DialogEditEmailFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_edit_account, null);
        builder.setView(view);
        builder.setTitle(R.string.dialog_edit_email);

        userInput = (EditText) view.findViewById(R.id.user_input);
        userInput.setText(DataManager.getInstance().getUserLogged().getEmail());

        builder.setPositiveButton(R.string.option_ok, null);
        builder.setNegativeButton(R.string.option_cancel, null);
        return builder.create();
    }


    @Override
    public void onResume() {
        super.onResume();
        AlertDialog alertDialog = (AlertDialog) getDialog();

        Button saveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;
                View focusView = null;

                if (!(userInput.getText().toString()).contains("@")) {
                    userInput.setError(getString(R.string.error_invalid_email));
                    focusView = userInput;
                    cancel = true;
                }

                if (TextUtils.isEmpty(userInput.getText().toString())) {
                    userInput.setError(getString(R.string.error_field_required));
                    focusView = userInput;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    saveInformation();
                    dismiss();
                }
            }
        });

        Button cancelButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void saveInformation() {
        DataManager.getInstance().getUserLogged().setEmail(userInput.getText().toString());
        DataManager.getInstance().getDataBase().updateUser(DataManager.getInstance().getUserLogged());
        ViewManager.getInstance().updateNavigationProfile(getActivity().getApplicationContext());

        ViewManager.getInstance().updateInformation();
    }

}
