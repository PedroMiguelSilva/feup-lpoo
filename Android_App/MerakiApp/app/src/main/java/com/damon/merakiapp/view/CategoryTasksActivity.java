package com.damon.merakiapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.DataManager;
import com.damon.merakiapp.logic.Task;

import java.util.ArrayList;
import java.util.List;

public class CategoryTasksActivity extends AppCompatActivity {

    private List<Task> tasks;
    private ListView listView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Task> categoryTasks = DataManager.getInstance().getDataBase().getCategoryTasks(ViewManager.getInstance().getCurrentCategory().getId());

        if (ViewManager.getInstance().getCurrentCategory() != null && categoryTasks != null)
            this.tasks = categoryTasks;
        else this.tasks = new ArrayList<Task>();

        setContentView(R.layout.activity_category_tasks);
        createToolbar();
        createFloatingButton();

        this.listView = (ListView) findViewById(R.id.list_tasks);
        ViewManager.getInstance().setListAdapter(new ListAdapter(this, tasks));
        this.listView.setAdapter(ViewManager.getInstance().getListAdapter());
    }

    private void createToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(ViewManager.getInstance().getCurrentCategory().getName());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createFloatingButton() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_category);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewManager.getInstance().setManageTask(false);
                Intent intent = new Intent(CategoryTasksActivity.this, ManageTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (ViewManager.getInstance().getCurrentCategory().getName().equals("Default"))
            getMenuInflater().inflate(R.menu.main_menu, menu);
        else
            getMenuInflater().inflate(R.menu.category_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            ViewManager.getInstance().setCurrentCategory(null);
            ViewManager.getInstance().updateInformation();
            finish();
        } else if (id == R.id.action_refresh) {
            ViewManager.getInstance().updateInformation();
            toolbar.setTitle(ViewManager.getInstance().getCurrentCategory().getName());
        } else if (id == R.id.action_edit) {
            ViewManager.getInstance().setManageCategory(true);
            DialogManageCategoryFragment dialog = DialogManageCategoryFragment.newInstance("Edit Category");
            dialog.show(getSupportFragmentManager(), "DialogManageCategoryFragment");
        } else if (id == R.id.action_delete) {
            deleteCategory();
            finish();
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(CategoryTasksActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteCategory() {
        DataManager.getInstance().getDataBase().deleteCategory(ViewManager.getInstance().getCurrentCategory());
        ViewManager.getInstance().updateLists();
        ViewManager.getInstance().updateInformation();
    }

}