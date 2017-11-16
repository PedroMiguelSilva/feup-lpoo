package com.damon.merakiapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.damon.merakiapp.logic.Category;
import com.damon.merakiapp.logic.DataManager;
import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.Task;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class ManageTaskActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText taskInput;
    private Button buttonDelete;
    private Button buttonArchive;
    private Spinner dateSpinner;
    private Spinner categorySpinner;

    private ArrayList<String> categories;
    private boolean updateCoords = false;
    private LatLng coords;

    private int dateSpinnerPos;
    private int catSpinnerPos;
    private boolean toArchive;

    private int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.taskInput = (EditText) findViewById(R.id.input_task);

        this.categories = new ArrayList<String>();

        prepareDisplay();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createDateSpinner();
        createCategorySpinner();

        createButtons();

        if (!ViewManager.getInstance().isManageTask()) {
            buttonDelete.setVisibility(View.GONE);
            buttonArchive.setVisibility(View.GONE);
        } else {
            this.toArchive = ViewManager.getInstance().getTaskToEdit().isArchived();

            if (!ViewManager.getInstance().getTaskToEdit().isComplete())
                buttonArchive.setVisibility(View.GONE);
        }

    }

    private void prepareDisplay() {

        ArrayList<Category> userCategories = DataManager.getInstance().getDataBase().getUserCategories(DataManager.getInstance().getUserLogged().getId());

        for (int i = 0; i < userCategories.size(); i++)
            this.categories.add(userCategories.get(i).getName());

        if (ViewManager.getInstance().isManageTask()) {
            toolbar.setTitle(R.string.edit_task_view);
            this.taskInput.setText(ViewManager.getInstance().getTaskToEdit().getText());
            this.dateSpinnerPos = ViewManager.getInstance().getDatePos(ViewManager.getInstance().getTaskToEdit().getDate());
            this.catSpinnerPos = ViewManager.getInstance().getCategoryPos();
        } else {
            toolbar.setTitle(R.string.add_task_view);
            this.taskInput.setHint(R.string.add_hint);
            this.dateSpinnerPos = 0;

            if (ViewManager.getInstance().getCurrentCategory() != null) {
                for (int i = 0; i < userCategories.size(); i++) {
                    if (ViewManager.getInstance().getCurrentCategory().getId() == userCategories.get(i).getId())
                        this.catSpinnerPos = i;
                }
            } else
                this.catSpinnerPos = 0;
        }
    }

    private void createButtons() {
        createLocationButton();
        createArchiveButton();
        createSaveButton();
        createDeleteButton();
    }

    private void createLocationButton() {
        Button button = (Button) findViewById(R.id.select_location_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(ManageTaskActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createArchiveButton() {
        buttonArchive = (Button) findViewById(R.id.archive_button);
        buttonArchive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String disclaimer;

                if (toArchive) {
                    toArchive = false;
                    disclaimer = "Task will be no longer archived!";
                } else {
                    toArchive = true;
                    disclaimer = "Task will be archived!";
                }

                Toast.makeText(view.getContext(), disclaimer, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createSaveButton() {

        Button button = (Button) findViewById(R.id.buttonSave);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String taskText = taskInput.getText().toString();

                boolean cancel = false;
                View focusView = null;

                if (TextUtils.isEmpty(taskText)) {
                    taskInput.setError(getString(R.string.error_field_required));
                    focusView = taskInput;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    ArrayList<Category> userCategories = DataManager.getInstance().getDataBase().getUserCategories(DataManager.getInstance().getUserLogged().getId());

                    if (ViewManager.getInstance().isManageTask())
                        updateTask(taskText, userCategories);
                    else
                        addTask(taskText, userCategories);

                    ViewManager.getInstance().updateLists();
                    ViewManager.getInstance().updateInformation();
                    finish();
                }
            }
        });

    }

    private void updateTask(String taskText, ArrayList<Category> userCategories) {
        ViewManager.getInstance().getTaskToEdit().setText(taskText);
        ViewManager.getInstance().getTaskToEdit().setDate(ViewManager.getInstance().getDateString(dateSpinnerPos));
        ViewManager.getInstance().getTaskToEdit().setCategoryId(userCategories.get(catSpinnerPos).getId());
        ViewManager.getInstance().getTaskToEdit().setArchived(toArchive);
        if (updateCoords) ViewManager.getInstance().getTaskToEdit().setCoords(coords);

        DataManager.getInstance().getDataBase().updateTask(ViewManager.getInstance().getTaskToEdit());
    }

    private void addTask(String taskText, ArrayList<Category> userCategories) {
        Task task = new Task(taskText, ViewManager.getInstance().getDateString(dateSpinnerPos), false, userCategories.get(catSpinnerPos).getId());
        if (updateCoords) task.setCoords(coords);

        DataManager.getInstance().getDataBase().addTask(task);
    }

    private void createDeleteButton() {
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DataManager.getInstance().getDataBase().deleteTask(ViewManager.getInstance().getTaskToEdit());
                ViewManager.getInstance().updateInformation();
                finish();
            }
        });
    }

    private void createDateSpinner() {

        dateSpinner = (Spinner) findViewById(R.id.date_spinner);
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ViewManager.getInstance().getTaskDates());
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(dateAdapter);
        dateSpinner.setSelection(dateSpinnerPos);

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                dateSpinnerPos = dateSpinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

    }

    private void createCategorySpinner() {

        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.categories);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(catAdapter);
        categorySpinner.setSelection(catSpinnerPos);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                catSpinnerPos = categorySpinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                this.coords = place.getLatLng();
                updateCoords = true;

                String address = String.format("Place: %s", place.getName());
                Toast.makeText(this, address, Toast.LENGTH_LONG).show();
            }
        }
    }

}