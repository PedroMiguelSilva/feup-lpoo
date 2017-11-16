package com.damon.merakiapp.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.damon.merakiapp.R;
import com.damon.merakiapp.logic.Category;
import com.damon.merakiapp.logic.DataManager;
import com.damon.merakiapp.logic.Task;
import com.damon.merakiapp.logic.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewManager {

    /**
     * Singleton instance
     */
    static private ViewManager instance = null;

    /**
     * Navigation drawer view
     */
    private NavigationView navigationView = null;

    /**
     * Expandable list adapter
     */
    private ExpandableListAdapter expandableListAdapter;

    /**
     * Main / Home activity
     */
    private Activity mainActivity;

    /**
     * Main fragment
     */
    private MainFragment mainFragment = null;

    /**
     * Maps fragment
     */
    private MapsFragment mapsFragment = null;

    /**
     * Category fragment
     */
    private CategoryFragment categoryFragment = null;

    /**
     * About fragment
     */
    private AboutFragment aboutFragment = null;

    /**
     * Archive fragment
     */
    private ArchiveFragment archiveFragment = null;

    /**
     * Login fragment
     */
    private LoginFragment loginFragment = null;

    /**
     * Header list for expandable list adapter
     */
    private final List<String> listDataHeader;

    /**
     * Child list for expandable list adapter
     */
    private final HashMap<String, List<Task>> listHash;

    /**
     * Today's list of tasks for expandable list adapter
     */
    private final List<Task> today;

    /**
     * Tomorrow's list of tasks for expandable list adapter
     */
    private final List<Task> tomorrow;

    /**
     * Upcoming's list of tasks for expandable list adapter
     */
    private final List<Task> upcoming;

    /**
     * Someday's list of tasks for expandable list adapter
     */
    private final List<Task> someday;

    /**
     * Boolean for Manage Task Activity (add or edit task)
     */
    private boolean manageTask;

    /**
     * Task to edit on Manage Task Activity
     */
    private Task taskToEdit = null;

    /**
     * Current category for viewing its tasks in Category Tasks Activity
     */
    private Category currentCategory = null;

    /**
     * Grid adapter
     */
    private GridAdapter gridAdapter = null;

    /**
     * Tasks by category list adapter
     */
    private ListAdapter listAdapter = null;

    /**
     * Archived tasks list adapter
     */
    private ListAdapter archiveAdapter = null;

    /**
     * Boolean for list adapter's button display (distinguish archived tasks from by category tasks)
     */
    private boolean archiveView = false;

    /**
     * Boolean for Manage Category Dialog (add or edit category)
     */
    private boolean manageCategory;

    /**
     * Arraylist of dates (today, tomorrow, upcoming, someday)
     */
    private final ArrayList<String> taskDates;

    /**
     * Boolean to add category
     */
    private boolean addCategory = false;

    /**
     * Boolean user login type
     */
    private boolean userLogin = false;

    /**
     * User's profile picture on Navigation
     */
    private de.hdodenhof.circleimageview.CircleImageView navProfilePicture;


    /**
     * Gets singleton instance
     *
     * @return singleton instance
     */
    static public ViewManager getInstance() {
        if (instance == null)
            instance = new ViewManager();
        return instance;
    }

    /**
     * View Manager constructor
     */
    private ViewManager() {
        taskDates = new ArrayList<>();
        taskDates.add("Today");
        taskDates.add("Tomorrow");
        taskDates.add("Upcoming");
        taskDates.add("Someday");

        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add(taskDates.get(0));
        listDataHeader.add(taskDates.get(1));
        listDataHeader.add(taskDates.get(2));
        listDataHeader.add(taskDates.get(3));

        today = new ArrayList<>();
        tomorrow = new ArrayList<>();
        upcoming = new ArrayList<>();
        someday = new ArrayList<>();

        updateLists();
    }

    /**
     * Gets navigation view
     *
     * @return navigation view
     */
    public NavigationView getNavigationView() {
        return this.navigationView;
    }

    /**
     * Sets navigation view
     *
     * @param view navigation
     */
    public void setNavigationView(NavigationView view) {
        this.navigationView = view;
    }

    /**
     * Updates navigation view according to user's profile
     *
     * @param mainContext activity's context
     */
    public void updateNavigationProfile(Context mainContext) {
        View header = this.navigationView.getHeaderView(0);
        this.navProfilePicture = (de.hdodenhof.circleimageview.CircleImageView) header.findViewById(R.id.nav_profile_image);
        TextView navUser = (TextView) header.findViewById(R.id.userName);
        TextView navEmail = (TextView) header.findViewById(R.id.userEmail);

        navUser.setText(DataManager.getInstance().getUserLogged().getName());
        navEmail.setText(DataManager.getInstance().getUserLogged().getEmail());

        if (DataManager.getInstance().getUserLogged().isGoogleUser()) {
            Glide.with(mainContext).load(DataManager.getInstance().getUserLogged().getPhotoUrl())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(this.navProfilePicture);
        } else {
            setProfilePicture(DataManager.getInstance().getUserLogged().getBitmap());
        }
    }

    /**
     * Sets user's profile picture on navigation
     *
     * @param bitmap Bitmap
     */
    private void setProfilePicture(Bitmap bitmap) {
        if (bitmap != null)
            this.navProfilePicture.setImageBitmap(bitmap);
    }

    /**
     * Gets expandable list adapter
     *
     * @return expandable list adapter
     */
    public ExpandableListAdapter getExpandableListAdapter() {
        return this.expandableListAdapter;
    }

    /**
     * Sets expandable list adapter
     *
     * @param listAdapter List Adapter
     */
    public void setExpandableListAdapter(ExpandableListAdapter listAdapter) {
        this.expandableListAdapter = listAdapter;
    }

    /**
     * Updates expandable list adapter
     */
    private void updateExpandableListAdapter() {
        if (this.expandableListAdapter != null) {
            updateLists();
            this.expandableListAdapter.updateAdapter(this.listDataHeader, this.listHash);
        }
    }

    /**
     * Gets header list for expandable list adapter
     *
     * @return header list
     */
    public List<String> getListDataHeader() {
        return this.listDataHeader;
    }

    /**
     * Gets child list for expandable list adapter
     *
     * @return child list
     */
    public HashMap<String, List<Task>> getListHash() {
        return this.listHash;
    }

    /**
     * Updates today, tomorrow, upcoming and someday's lists
     */
    public void updateLists() {

        today.clear();
        tomorrow.clear();
        upcoming.clear();
        someday.clear();

        ArrayList<Task> userTasks = new ArrayList<>();

        User user = DataManager.getInstance().getUserLogged();
        if (user != null)
            userTasks = DataManager.getInstance().getDataBase().getUserTasks(user.getId(), false);

        if (userTasks != null) {
            for (int i = 0; i < userTasks.size(); i++) {
                if (userTasks.get(i).getDate().equals(taskDates.get(0)))
                    today.add(userTasks.get(i));
                if (userTasks.get(i).getDate().equals(taskDates.get(1)))
                    tomorrow.add(userTasks.get(i));
                if (userTasks.get(i).getDate().equals(taskDates.get(2)))
                    upcoming.add(userTasks.get(i));
                if (userTasks.get(i).getDate().equals(taskDates.get(3)))
                    someday.add(userTasks.get(i));
            }
        }

        listHash.put(listDataHeader.get(0), today);
        listHash.put(listDataHeader.get(1), tomorrow);
        listHash.put(listDataHeader.get(2), upcoming);
        listHash.put(listDataHeader.get(3), someday);
    }

    /**
     * Gets main activity
     *
     * @return main activity
     */
    public Activity getMainActivity() {
        return this.mainActivity;
    }

    /**
     * Sets main activity
     *
     * @param activity Acticity
     */
    public void setMainActivity(Activity activity) {
        this.mainActivity = activity;
    }

    /**
     * Gets main fragment
     *
     * @return main fragment
     */
    public MainFragment getMainFragment() {
        return this.mainFragment;
    }

    /**
     * Sets main fragment
     *
     * @param f fragment
     */
    public void setMainFragment(MainFragment f) {
        this.mainFragment = f;
    }

    /**
     * Gets maps fragment
     *
     * @return maps fragment
     */
    public MapsFragment getMapsFragment() {
        return this.mapsFragment;
    }

    /**
     * Sets maps fragment
     *
     * @param f fragment
     */
    public void setMapsFragment(MapsFragment f) {
        this.mapsFragment = f;
    }

    /**
     * Gets category fragment
     *
     * @return category fragment
     */
    public CategoryFragment getCategoryFragment() {
        return this.categoryFragment;
    }

    /**
     * Sets category fragment
     *
     * @param f fragment
     */
    public void setCategoryFragment(CategoryFragment f) {
        this.categoryFragment = f;
    }

    /**
     * Gets about fragment
     *
     * @return about fragment
     */
    public AboutFragment getAboutFragment() {
        return this.aboutFragment;
    }

    /**
     * Sets about fragment
     *
     * @param f fragment
     */
    public void setAboutFragment(AboutFragment f) {
        this.aboutFragment = f;
    }

    /**
     * Gets archive fragment
     *
     * @return archive fragment
     */
    public ArchiveFragment getArchiveFragment() {
        return this.archiveFragment;
    }

    /**
     * Sets archive fragment
     *
     * @param f fragment
     */
    public void setArchiveFragment(ArchiveFragment f) {
        this.archiveFragment = f;
    }

    /**
     * Gets login fragment
     *
     * @return login fragment
     */
    public LoginFragment getLoginFragment() {
        return this.loginFragment;
    }

    /**
     * Sets login fragment
     *
     * @param f fragment
     */
    public void setLoginFragment(LoginFragment f) {
        this.loginFragment = f;
    }

    /**
     * Clear's login fragment data
     */
    public void clearLoginFragment() {
        this.loginFragment.clearData();
    }

    /**
     * Gets manageTask
     * Useful for Manage Task Activity to distinguish add from edit task
     *
     * @return true for edit or false for add
     */
    public boolean isManageTask() {
        return this.manageTask;
    }

    /**
     * Sets manageTask
     * User decides to add or edit(manage) task
     *
     * @param b boolean
     */
    public void setManageTask(boolean b) {
        this.manageTask = b;
    }

    /**
     * Gets task to edit
     *
     * @return task to edit
     */
    public Task getTaskToEdit() {
        return this.taskToEdit;
    }

    /**
     * Sets task to edit when user want's to edit one
     *
     * @param t task
     */
    public void setTaskToEdit(Task t) {
        this.taskToEdit = t;
    }

    /**
     * Gets tasksToEdit's category's position
     *
     * @return position
     */
    public int getCategoryPos() {
        ArrayList<Category> userCategories = DataManager.getInstance().getDataBase().getUserCategories(DataManager.getInstance().getUserLogged().getId());

        for (int i = 0; i < userCategories.size(); i++) {
            if (taskToEdit.getCategoryId() == userCategories.get(i).getId())
                return i;
        }
        return -1;
    }

    /**
     * Gets current category
     *
     * @return current category
     */
    public Category getCurrentCategory() {
        return this.currentCategory;
    }

    /**
     * Sets current category when user want's to view it's tasks
     *
     * @param c category
     */
    public void setCurrentCategory(Category c) {
        this.currentCategory = c;
    }

    /**
     * Gets grid adapter
     *
     * @return grid adapter
     */
    public GridAdapter getGridAdapter() {
        return this.gridAdapter;
    }

    /**
     * Sets grid adapter
     *
     * @param gridAdapter Grid Adapter
     */
    public void setGridAdapter(GridAdapter gridAdapter) {
        this.gridAdapter = gridAdapter;
    }

    /**
     * Updates grid adapter
     */
    private void updateGridAdapter() {
        if (this.gridAdapter != null)
            this.gridAdapter.updateAdapter();
    }

    /**
     * Gets tasks by category list adapter
     *
     * @return list adapter
     */
    public ListAdapter getListAdapter() {
        return this.listAdapter;
    }

    /**
     * Sets tasks by category list adapter
     *
     * @param listAdapter List Adapter
     */
    public void setListAdapter(ListAdapter listAdapter) {
        this.listAdapter = listAdapter;
    }

    /**
     * Updates tasks by category list adapter
     */
    private void updateListAdapter() {
        if (this.listAdapter != null)
            if (currentCategory != null)
                this.listAdapter.updateAdapter(DataManager.getInstance().getDataBase().getCategoryTasks(this.currentCategory.getId()));
    }

    /**
     * Gets archive list adapter
     *
     * @return list adapter
     */
    public ListAdapter getArchiveAdapter() {
        return this.archiveAdapter;
    }

    /**
     * Sets archive list adapter
     *
     * @param listAdapter List Adapter
     */
    public void setArchiveAdapter(ListAdapter listAdapter) {
        this.archiveAdapter = listAdapter;
    }

    /**
     * Updates archive list adapter
     */
    private void updateArchiveAdapter() {
        if (this.archiveAdapter != null) {
            int userId = DataManager.getInstance().getUserLogged().getId();
            this.archiveAdapter.updateAdapter(DataManager.getInstance().getDataBase().getUserTasks(userId, true));
        }
    }

    /**
     * Gets boolean archiveView
     * Useful for list adapter's button display
     *
     * @return true for archived tasks or false for tasks by category
     */
    public boolean isArchiveView() {
        return this.archiveView;
    }

    /**
     * Sets boolean archiveView
     * Useful for list adapter's button display
     *
     * @param b boolean
     */
    public void setArchiveView(boolean b) {
        this.archiveView = b;
    }

    /**
     * Gets boolean manageCategory
     * Useful for Manage Category Dialog (add or edit category)
     *
     * @return true for edit or false for add
     */
    public boolean isManageCategory() {
        return this.manageCategory;
    }

    /**
     * Sets boolean manageCategory
     * When user wants to add or edit a category
     *
     * @param b boolean
     */
    public void setManageCategory(boolean b) {
        this.manageCategory = b;
    }

    /**
     * Gets arraylist of dates
     *
     * @return task dates arraylist
     */
    public ArrayList<String> getTaskDates() {
        return this.taskDates;
    }

    /**
     * Gets the position of a date
     *
     * @param date string
     * @return date position
     */
    public int getDatePos(String date) {
        for (int i = 0; i < taskDates.size(); i++)
            if (taskDates.get(i).equals(date))
                return i;
        return -1;
    }

    /**
     * Gets date
     *
     * @param pos int position
     * @return date string
     */
    public String getDateString(int pos) {
        return taskDates.get(pos);
    }

    /**
     * Gets boolean addCategory
     * Useful for floating button's listener
     *
     * @return true for add category or false for add task
     */
    public boolean isAddCategory() {
        return this.addCategory;
    }

    /**
     * Sets boolean addCategory
     * When floating button's listener changes
     *
     * @param b boolean
     */
    public void setAddCategory(boolean b) {
        this.addCategory = b;
    }

    /**
     * Gets boolean userLogin
     * Useful for app to know the login type
     *
     * @return true for login or false for register
     */
    public boolean isUserLogin() {
        return this.userLogin;
    }

    /**
     * Sets boolean userLogin
     * When user decides a login type
     *
     * @param b boolean
     */
    public void setUserLogin(boolean b) {
        this.userLogin = b;
    }

    /**
     * Updates all adapters (expandable list, grid, tasks by category and archive list)
     */
    public void updateInformation() {
        updateExpandableListAdapter();
        updateGridAdapter();
        updateListAdapter();
        updateArchiveAdapter();
    }

}
