package com.damon.merakiapp.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.DataManager;
import com.damon.merakiapp.logic.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private SignInButton signInGoogle;
    public static final int SIGN_IN_CODE = 777;

    private Toolbar toolbar = null;
    private Button registerButton;
    private Button signinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createToolbar();
        createRegisterButton();
        createSignInButton();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        signInGoogle = (SignInButton) findViewById(R.id.googleSignIn);
        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });
    }

    private void createToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.login_view);
        setSupportActionBar(toolbar);
    }

    private void createRegisterButton() {

        registerButton = (Button) findViewById(R.id.main_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ViewManager.getInstance().setUserLogin(false);
                openLoginFragment();
            }
        });
    }

    private void createSignInButton() {

        signinButton = (Button) findViewById(R.id.main_signin_button);
        signinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ViewManager.getInstance().setUserLogin(true);
                openLoginFragment();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();
            boolean loginSuccess;

            String photoUrl = account.getPhotoUrl().toString();

            User googleUser = new User(account.getDisplayName(), account.getEmail(), "google", true);
            googleUser.setPhotoUrl(photoUrl);

            loginSuccess = DataManager.getInstance().login(googleUser);

            if (loginSuccess) {
                Auth.GoogleSignInApi.signOut(googleApiClient);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "There's already an account associated with this email", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Sign In failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void openLoginFragment() {
        hideButtons();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_login, ViewManager.getInstance().getLoginFragment());
        fragmentTransaction.commit();
    }

    private void hideButtons() {
        registerButton.setVisibility(View.GONE);
        signinButton.setVisibility(View.GONE);
        signInGoogle.setVisibility(View.GONE);
    }

    private void showButtons() {
        registerButton.setVisibility(View.VISIBLE);
        signinButton.setVisibility(View.VISIBLE);
        signInGoogle.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ViewManager.getInstance().clearLoginFragment();
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fragment_container_login)).commit();
            showButtons();
        }

        return super.onOptionsItemSelected(item);
    }

}

