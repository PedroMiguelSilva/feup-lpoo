package com.damon.merakiapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.DataManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar = null;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private FloatingActionButton fabButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createToolbar();

        openMainFragment();

        createFloatingButton();
        createNavigation();

        ViewManager.getInstance().setMainActivity(this);
    }

    private void createToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.main_view);
        setSupportActionBar(toolbar);
    }

    private void openMainFragment() {
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, ViewManager.getInstance().getMainFragment());
        fragmentTransaction.commit();
    }

    private void openMapsFragment() {
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, ViewManager.getInstance().getMapsFragment());
        fragmentTransaction.commit();
    }

    private void openCategoryFragment() {
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, ViewManager.getInstance().getCategoryFragment());
        fragmentTransaction.commit();
    }

    private void openAboutFragment() {
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, ViewManager.getInstance().getAboutFragment());
        fragmentTransaction.commit();
    }

    private void openArchiveFragment() {
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, ViewManager.getInstance().getArchiveFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            ViewManager.getInstance().updateInformation();
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {

            toolbar.setTitle(R.string.main_view);
            fabButton.show();
            ViewManager.getInstance().setAddCategory(false);
            ViewManager.getInstance().setArchiveView(false);
            openMainFragment();

        } else if (id == R.id.nav_map) {

            toolbar.setTitle(R.string.map_view);
            fabButton.hide();
            openMapsFragment();

        } else if (id == R.id.nav_categories) {

            toolbar.setTitle(R.string.category_view);
            fabButton.show();
            ViewManager.getInstance().setAddCategory(true);
            openCategoryFragment();

        } else if (id == R.id.nav_about) {

            toolbar.setTitle(R.string.about_view);
            fabButton.hide();
            openAboutFragment();

        } else if (id == R.id.nav_archive) {

            toolbar.setTitle(R.string.nav_archive);
            fabButton.hide();
            ViewManager.getInstance().setArchiveView(true);
            openArchiveFragment();

        } else if (id == R.id.nav_signout) {
            DataManager.getInstance().logout();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void createFloatingButton() {

        fabButton = (FloatingActionButton) findViewById(R.id.fab_main);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ViewManager.getInstance().isAddCategory()) {
                    ViewManager.getInstance().setManageCategory(false);

                    DialogManageCategoryFragment dialog = DialogManageCategoryFragment.newInstance("Add Category");
                    dialog.show(getSupportFragmentManager(), "DialogManageCategoryFragment");

                } else {
                    ViewManager.getInstance().setManageTask(false);

                    Intent intent = new Intent(MainActivity.this, ManageTaskActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void createNavigation() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        ViewManager.getInstance().setNavigationView((NavigationView) findViewById(R.id.nav_view));
        ViewManager.getInstance().getNavigationView().setNavigationItemSelectedListener(this);
        ViewManager.getInstance().updateNavigationProfile(getApplicationContext());
    }

}

