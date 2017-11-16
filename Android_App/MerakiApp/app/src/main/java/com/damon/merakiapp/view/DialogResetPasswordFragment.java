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

public class DialogResetPasswordFragment extends DialogFragment {

    private EditText inputPassword;
    private EditText confirmPassword;

    public static DialogResetPasswordFragment newInstance(String title) {
        DialogResetPasswordFragment frag = new DialogResetPasswordFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_reset_password, null);
        builder.setView(view);
        builder.setTitle(R.string.dialog_reset_password);

        inputPassword = (EditText) view.findViewById(R.id.input_password);
        confirmPassword = (EditText) view.findViewById(R.id.confirm_password);

        builder.setPositiveButton(R.string.option_save, null);
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

                if (!inputPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                    confirmPassword.setError(getString(R.string.error_password_mismatch));
                    focusView = confirmPassword;
                    cancel = true;
                }

                if (TextUtils.isEmpty(inputPassword.getText().toString())) {
                    inputPassword.setError(getString(R.string.error_field_required));
                    focusView = inputPassword;
                    cancel = true;
                } else if (inputPassword.getText().toString().length() < 6) {
                    inputPassword.setError(getString(R.string.error_invalid_password));
                    focusView = inputPassword;
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
        DataManager.getInstance().getUserLogged().setPassword(inputPassword.getText().toString());
        DataManager.getInstance().getDataBase().updateUser(DataManager.getInstance().getUserLogged());
        ViewManager.getInstance().updateNavigationProfile(getActivity().getApplicationContext());
    }

}
