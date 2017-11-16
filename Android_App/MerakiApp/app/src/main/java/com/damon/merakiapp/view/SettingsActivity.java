package com.damon.merakiapp.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.DataManager;

import java.io.IOException;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class SettingsActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 0;
    private static int RESULT_SELECT_IMAGE = 1;
    private Button profilePictureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        createToolbar();
        createButtons();
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createButtons() {
        createButtonProfilePicture();
        createButtonName();
        createButtonEmail();
        createButtonPassword();
        createButtonDeleteAccount();
        createButtonContentInformation();
        createButtonCleanArchive();
    }

    private void createButtonProfilePicture() {
        profilePictureButton = (Button) findViewById(R.id.button_profile);
        profilePictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!mayRequestExternalStorage())
                    return;
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_SELECT_IMAGE);
    }

    private void createButtonName() {
        Button button = (Button) findViewById(R.id.button_name);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogEditNameFragment dialog = DialogEditNameFragment.newInstance("Edit name");
                dialog.show(getSupportFragmentManager(), "DialogEditNameFragment");
            }
        });
    }

    private void createButtonEmail() {
        Button button = (Button) findViewById(R.id.button_email);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogEditEmailFragment dialog = DialogEditEmailFragment.newInstance("Edit email");
                dialog.show(getSupportFragmentManager(), "DialogEditEmailFragment");
            }
        });
    }

    private void createButtonPassword() {
        Button button = (Button) findViewById(R.id.button_password);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogResetPasswordFragment dialog = DialogResetPasswordFragment.newInstance("Reset password");
                dialog.show(getSupportFragmentManager(), "DialogResetPasswordFragment");
            }
        });
    }

    private void createButtonDeleteAccount() {
        Button button = (Button) findViewById(R.id.button_delete_account);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void createButtonContentInformation() {
        Button button = (Button) findViewById(R.id.button_display_content);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogContentFragment dialog = DialogContentFragment.newInstance("Content");
                dialog.show(getSupportFragmentManager(), "DialogContentFragment");
            }
        });
    }

    private void createButtonCleanArchive() {
        Button button = (Button) findViewById(R.id.button_clean_archived);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogCleanArchiveFragment dialog = DialogCleanArchiveFragment.newInstance("Clean Archive");
                dialog.show(getSupportFragmentManager(), "DialogCleanArchiveFragment");
            }
        });
    }

    private void showDialog() {
        DialogDeleteAccountFragment dialog = DialogDeleteAccountFragment.newInstance("Delete Account");
        dialog.show(getSupportFragmentManager(), "DeleteAccountFragment");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_SELECT_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                DataManager.getInstance().getUserLogged().setBitmap(bitmap);

                DataManager.getInstance().getDataBase().updateUser(DataManager.getInstance().getUserLogged());
                ViewManager.getInstance().updateNavigationProfile(getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean mayRequestExternalStorage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(profilePictureButton, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }


}
