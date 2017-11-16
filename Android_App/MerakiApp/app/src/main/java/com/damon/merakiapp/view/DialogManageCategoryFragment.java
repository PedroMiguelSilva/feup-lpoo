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
import com.damon.merakiapp.logic.Category;
import com.damon.merakiapp.logic.DataManager;

public class DialogManageCategoryFragment extends DialogFragment {

    private EditText categoryInput;

    public static DialogManageCategoryFragment newInstance(String title) {
        DialogManageCategoryFragment frag = new DialogManageCategoryFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_manage_category, null);
        builder.setView(view);
        categoryInput = (EditText) view.findViewById(R.id.category_input);

        if (ViewManager.getInstance().isManageCategory()) {
            builder.setTitle(R.string.edit_category_view);
            categoryInput.setText(ViewManager.getInstance().getCurrentCategory().getName());
        } else
            builder.setTitle(R.string.add_category_view);

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

                if (TextUtils.isEmpty(categoryInput.getText().toString())) {
                    categoryInput.setError(getString(R.string.error_field_required));
                    focusView = categoryInput;
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
        if (ViewManager.getInstance().isManageCategory()) {
            ViewManager.getInstance().getCurrentCategory().setName(categoryInput.getText().toString());
            DataManager.getInstance().getDataBase().updateCategory(ViewManager.getInstance().getCurrentCategory());
        } else {
            Category category = new Category(categoryInput.getText().toString(), DataManager.getInstance().getUserLogged().getId());
            DataManager.getInstance().getDataBase().addCategory(category);
        }

        ViewManager.getInstance().updateInformation();
    }
}
