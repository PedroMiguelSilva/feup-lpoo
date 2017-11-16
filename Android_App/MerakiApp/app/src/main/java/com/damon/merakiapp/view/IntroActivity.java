package com.damon.merakiapp.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.DataManager;

public class IntroActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    private TextView textTitle, textSlogan;
    private DataManager dataManager;
    private ViewManager viewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getSupportActionBar().hide();

        textTitle = (TextView) findViewById(R.id.merakiIntro);
        Typeface fontDido = Typeface.createFromAsset(getAssets(), "fonts/ufonts.com_didot.ttf");
        textTitle.setTypeface(fontDido);

        textSlogan = (TextView) findViewById(R.id.sloganIntro);
        Typeface fontBell = Typeface.createFromAsset(getAssets(), "fonts/BELLI.TTF");
        textSlogan.setTypeface(fontBell);

        createDataManager();
        createViewManger();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (DataManager.getInstance().getPreferences() == -1) {
                    Intent loginIntent = new Intent(IntroActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                } else {
                    Intent mainIntent = new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }

    private void createDataManager() {

        dataManager = DataManager.getInstance();
        dataManager.getInstance().setContext(this);
        dataManager.createDataBase();
        dataManager.getInstance().initPreferences();

    }

    private void createViewManger() {

        viewManager = ViewManager.getInstance();

        //Inicializacao dos fragmentos
        viewManager.setMainFragment(new MainFragment());
        viewManager.setMapsFragment(new MapsFragment());
        viewManager.setCategoryFragment(new CategoryFragment());
        viewManager.setAboutFragment(new AboutFragment());
        viewManager.setArchiveFragment(new ArchiveFragment());
        viewManager.setLoginFragment(new LoginFragment());

    }
}
