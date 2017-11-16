package com.damon.merakiapp.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
    /**
     * Data base version
     */
    private static final int DB_VERSION = 1;

    /**
     * Data base name
     */
    private static final String DB_NAME = "merakiDB";

    //User
    /**
     * User current logged in id
     */
    private static int userLoggedId = -1;

    //Users Table
    private static final String TABLE_USERS = "users";
    private static final String USER_ID = "_id";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_GOOGLEACCOUNT = "googleAccount";
    private static final String USER_GOOGLE_PHOTO = "photoUrl";
    private static final String USER_BITMAP_PHOTO = "bitmap";

    //Category Table
    private static final String TABLE_CATEGORIES = "categories";
    private static final String CATEGORY_ID = "_id";
    private static final String CATEGORY_NAME = "name";
    private static final String CATEGORY_USER_ID = "idUser";

    //Tasks Table
    private static final String TABLE_TASKS = "task";
    private static final String TASK_ID = "_id";
    private static final String TASK_NAME = "name";
    private static final String TASK_WHEN = "day";
    private static final String TASK_CHECKED = "checked";
    private static final String TASK_ARCHIVED = "archived";
    private static final String TASK_LATITUDE = "latitude";
    private static final String TASK_LONGITUDE = "longitude";
    private static final String TASK_CATEGORY_ID = "idCategory";

    /**
     * Creates data base
     *
     * @param context application context
     */
    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Creates the tables in a data base
     *
     * @param db data base
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "(" +
                USER_ID + " INTEGER PRIMARY KEY, " +
                USER_NAME + " STRING, " +
                USER_EMAIL + " STRING UNIQUE, " +
                USER_PASSWORD + " STRING, " +
                USER_GOOGLEACCOUNT + " INTEGER, " +
                USER_GOOGLE_PHOTO + " STRING, " +
                USER_BITMAP_PHOTO + " BLOB);";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "(" +
                CATEGORY_ID + " INTEGER PRIMARY KEY, " +
                CATEGORY_NAME + " STRING, " +
                CATEGORY_USER_ID + " INTEGER REFERENCES " + TABLE_USERS + "(" + USER_ID + ") ON DELETE CASCADE);";
        db.execSQL(CREATE_CATEGORIES_TABLE);

        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "(" +
                TASK_ID + " INTEGER PRIMARY KEY , " +
                TASK_NAME + " STRING, " +
                TASK_WHEN + " STRING, " +
                TASK_CHECKED + " INTEGER, " +
                TASK_ARCHIVED + " INTEGER, " +
                TASK_LATITUDE + " STRING, " +
                TASK_LONGITUDE + " STRING, " +
                TASK_CATEGORY_ID + " INTEGER REFERENCES " + TABLE_CATEGORIES + "(" + CATEGORY_ID + ") ON DELETE CASCADE);";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    /**
     * Upgrades data base
     *
     * @param db         data base
     * @param oldVersion old version
     * @param newVersion new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    /**
     * Opens data base
     *
     * @param db data base
     */
    @Override
    public void onOpen(SQLiteDatabase db) {

        super.onOpen(db);

        db.execSQL("PRAGMA foreign_keys = ON;");

    }

    /**
     * Convert from bitmap to byte array
     *
     * @param bitmap bitmap to be converted
     * @return byte array
     */
    private static byte[] getBytes(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        } else return null;

    }

    /**
     * convert from byte array to bitmap
     *
     * @param image byte array to be converted
     * @return bitmap
     */
    private static Bitmap getImage(byte[] image) {
        if (image != null) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        } else return null;

    }

    //---------------------------USERS---------------------------

    /**
     * Returns the id of the current logged user
     *
     * @return id of the current user
     */
    public int getUserLoggedId() {
        return userLoggedId;
    }

    /**
     * Sets the id of the current logged user
     *
     * @param userLoggedId id to be setted
     */
    public void setUserLoggedId(int userLoggedId) {
        DataBase.userLoggedId = userLoggedId;
    }

    /**
     * Adds user to data base
     *
     * @param user User to be added
     * @return user id in case of success and -1 otherwise
     */
    public int addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        long id;

        values.put(USER_NAME, user.getName());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_GOOGLEACCOUNT, (user.isGoogleUser()) ? 1 : 0);
        values.put(USER_GOOGLE_PHOTO, user.getPhotoUrl());
        values.put(USER_BITMAP_PHOTO, getBytes(user.getBitmap()));
        id = db.insert(TABLE_USERS, null, values);
        db.close();

        user.setId((int) id);

        if (id == -1)
            return -1;
        else return (int) id;

    }

    /**
     * Verifies if the email and password are of a user in the data base
     *
     * @param email    email to be verified
     * @param password password to be verified
     * @return true if a user in the data base has those credentials, false if not
     */
    public boolean login(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{USER_ID}, USER_EMAIL + "=? AND " + USER_PASSWORD + "=?", new String[]{email, password}, null, null, null);

        if (!cursor.moveToFirst()) return false;

        userLoggedId = Integer.parseInt(cursor.getString(0));


        return true;
    }

    /**
     * Returns the user in the data base with the id passed as argument
     *
     * @param id id of the user wanted
     * @return user in case of success, null otherwise
     */
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{USER_ID, USER_NAME,
                        USER_EMAIL, USER_PASSWORD, USER_GOOGLEACCOUNT, USER_GOOGLE_PHOTO, USER_BITMAP_PHOTO}, USER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (!cursor.moveToFirst()) return null;


        boolean googleUser;
        googleUser = cursor.getString(4).equals("1");

        User user = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3), googleUser);
        user.setPhotoUrl(cursor.getString(5));
        user.setBitmap(getImage(cursor.getBlob(6)));
        user.setId(Integer.parseInt(cursor.getString(0)));

        return user;
    }

    /**
     * Updates the information of the user passed as argument in the data base
     *
     * @param user user to be updated
     */
    public void updateUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME, user.getName());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_GOOGLE_PHOTO, user.getPhotoUrl());
        values.put(USER_BITMAP_PHOTO, getBytes(user.getBitmap()));

        db.update(TABLE_USERS, values, USER_ID + "=?", new String[]{String.valueOf(user.getId())});

        db.close();
    }

    /**
     * Deletes the user passed as argument of the data base
     *
     * @param user user to be deleted
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, USER_ID + "=" + user.getId(), null);
        db.close();
    }

    /**
     * Deletes all users in the data base
     */
    public void deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, null, null);
        db.close();
    }

    //---------------------------CATEGORIES---------------------------

    /**
     * Adds category to data base
     *
     * @param category category to be added
     * @return category id in case of success, -1 otherwise
     */
    public int addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        long id;

        values.put(CATEGORY_NAME, category.getName());
        values.put(CATEGORY_USER_ID, category.getUserId());
        id = db.insert(TABLE_CATEGORIES, null, values);
        db.close();

        category.setId((int) id);

        return (int) id;
    }

    /**
     * Returns the category in the data base with the id passed as argument
     *
     * @param id id of the category wanted
     * @return category in case of success, null otherwise
     */
    public Category getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORIES, new String[]{CATEGORY_ID, CATEGORY_NAME, CATEGORY_USER_ID}, CATEGORY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (!cursor.moveToFirst()) return null;

        cursor.moveToFirst();

        Category category = new Category(cursor.getString(1), Integer.parseInt(cursor.getString(2)));
        category.setId(Integer.parseInt(cursor.getString(0)));

        return category;
    }

    /**
     * Returns all the categories of the user passed as argument in the data base
     *
     * @param UserId id of the owner of the wanted categories
     * @return list of categories in case of success, null otherwise
     */
    public ArrayList<Category> getUserCategories(int UserId) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Category> categoriesList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_CATEGORIES, new String[]{CATEGORY_ID, CATEGORY_NAME, CATEGORY_USER_ID}, CATEGORY_USER_ID + "=?",
                new String[]{String.valueOf(UserId)}, null, null, null, null);


        if (!cursor.moveToFirst())
            return null;


        if (cursor.moveToFirst()) {

            do {

                Category category = new Category(
                        cursor.getString(1), Integer.parseInt(cursor.getString(2)));
                category.setId(Integer.parseInt(cursor.getString(0)));
                categoriesList.add(category);
            } while (cursor.moveToNext());

        }

        return categoriesList;
    }

    /**
     * Updates the information of the category passed as argument in the data base
     *
     * @param category category to be updated
     */
    public void updateCategory(Category category) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CATEGORY_NAME, category.getName());
        values.put(CATEGORY_USER_ID, category.getUserId());

        db.update(TABLE_CATEGORIES, values, CATEGORY_ID + "=?", new String[]{String.valueOf(category.getId())});

        db.close();
    }

    /**
     * Deletes the category passed as argument of the data base
     *
     * @param category category to be deleted
     */
    public void deleteCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORIES, CATEGORY_ID + "=" + category.getId(), null);
        db.close();
    }

    /**
     * Deletes all categories in the data base
     */
    public void deleteAllCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORIES, null, null);
        db.close();
    }

    //---------------------------TASKS---------------------------

    /**
     * Adds task passed as argument to data base
     *
     * @param task task to be added
     * @return task id in case of success, -1 otherwise
     */
    public int addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        long id;
        int checked = (task.isComplete()) ? 1 : 0;
        int archived = (task.isArchived()) ? 1 : 0;

        values.put(TASK_NAME, task.getText());
        values.put(TASK_WHEN, task.getDate());
        values.put(TASK_CHECKED, checked);
        values.put(TASK_ARCHIVED, archived);
        if (task.getCoords() != null) {
            values.put(TASK_LATITUDE, String.valueOf(task.getCoords().latitude));
            values.put(TASK_LONGITUDE, String.valueOf(task.getCoords().longitude));
        }

        values.put(TASK_CATEGORY_ID, task.getCategoryId());
        id = db.insert(TABLE_TASKS, null, values);
        db.close();

        task.setId((int) id);

        return (int) id;
    }

    /**
     * Returns the task in the data base with the id passed as argument
     *
     * @param id id of the task wanted
     * @return id of the task in case of success, -1 otherwise
     */
    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[]{TASK_ID, TASK_NAME,
                        TASK_WHEN, TASK_CHECKED, TASK_ARCHIVED, TASK_LATITUDE, TASK_LONGITUDE, TASK_CATEGORY_ID}, TASK_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (!cursor.moveToFirst()) return null;

        boolean checked;
        checked = cursor.getString(3).equals("1");

        boolean archived;
        archived = cursor.getString(4).equals("1");

        Task task = new Task(cursor.getString(1), cursor.getString(2), checked, Integer.parseInt(cursor.getString(7)));
        task.setArchived(archived);
        if (cursor.getString(5) != null && cursor.getString(6) != null) {
            Double latitude = Double.parseDouble(cursor.getString(5));
            Double longitude = Double.parseDouble(cursor.getString(6));
            task.setCoords(new LatLng(latitude, longitude));
        }
        task.setId(Integer.parseInt(cursor.getString(0)));

        return task;
    }

    /**
     * Returns all the not archived tasks of the category passed as argument in the data base
     *
     * @param CategoryId id of the category
     * @return lists of tasks in case of success, null otherwise
     */
    public ArrayList<Task> getCategoryTasks(int CategoryId) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Task> tasksList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_TASKS, new String[]{TASK_ID, TASK_NAME,
                        TASK_WHEN, TASK_CHECKED, TASK_ARCHIVED, TASK_LATITUDE, TASK_LONGITUDE, TASK_CATEGORY_ID}, TASK_CATEGORY_ID + "=? AND " + TASK_ARCHIVED + "=0",
                new String[]{String.valueOf(CategoryId)}, null, null, null, null);

        if (!cursor.moveToFirst()) return null;

        if (cursor.moveToFirst()) {

            do {

                boolean checked;
                checked = cursor.getString(3).equals("1");

                boolean archived;
                archived = cursor.getString(4).equals("1");

                Task task = new Task(
                        cursor.getString(1),
                        cursor.getString(2),
                        checked,
                        Integer.parseInt(cursor.getString(7)));

                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setArchived(archived);
                if (cursor.getString(5) != null && cursor.getString(6) != null) {
                    Double latitude = Double.parseDouble(cursor.getString(5));
                    Double longitude = Double.parseDouble(cursor.getString(6));
                    task.setCoords(new LatLng(latitude, longitude));
                }

                tasksList.add(task);
            } while (cursor.moveToNext());


        }

        return tasksList;
    }


    /**
     * Returns all the archived tasks or all the not archived tasks of the user passed as argument in the data base
     *
     * @param UserId        id of the user
     * @param archivedTasks true if we want all the archived tasks, false if we want all the nor archived tasks
     * @return lists of tasks in case of success, null otherwise
     */
    public ArrayList<Task> getUserTasks(int UserId, boolean archivedTasks) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Task> tasksList = new ArrayList<>();


        Cursor cursor = db.rawQuery("SELECT T." + TASK_ID + ", T." + TASK_NAME + ", T." + TASK_WHEN + ", T." + TASK_CHECKED + ", T." + TASK_ARCHIVED + ", T." + TASK_LATITUDE + ", T." + TASK_LONGITUDE + ", T." + TASK_CATEGORY_ID +
                " FROM " + TABLE_TASKS + " T" +
                " INNER JOIN " + TABLE_CATEGORIES + " C" +
                " ON T." + TASK_CATEGORY_ID + " = C." + CATEGORY_ID +
                " WHERE C." + CATEGORY_USER_ID + " = '" + UserId +
                "' AND T." + TASK_ARCHIVED + "=" + (archivedTasks ? 1 : 0) + ";", null);

        if (!cursor.moveToFirst()) return null;

        if (cursor.moveToFirst()) {

            do {

                boolean checked;
                checked = cursor.getString(3).equals("1");

                boolean archived;
                archived = cursor.getString(4).equals("1");

                Task task = new Task(
                        cursor.getString(1),
                        cursor.getString(2),
                        checked,
                        Integer.parseInt(cursor.getString(7)));

                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setArchived(archived);
                if (cursor.getString(5) != null && cursor.getString(6) != null) {
                    Double latitude = Double.parseDouble(cursor.getString(5));
                    Double longitude = Double.parseDouble(cursor.getString(6));
                    task.setCoords(new LatLng(latitude, longitude));
                }
                tasksList.add(task);
            } while (cursor.moveToNext());


        }

        return tasksList;
    }

    /**
     * Returns all the not archived tasks in a specific day of the user passed as argument
     *
     * @param UserId   id of the user
     * @param taskWhen day of the task
     * @return lists of tasks in case of success, null otherwise
     */
    public ArrayList<Task> getUserTasksByDay(int UserId, String taskWhen) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Task> tasksList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT T." + TASK_ID + ", T." + TASK_NAME + ", T." + TASK_WHEN + ", T." + TASK_CHECKED + ", T." + TASK_ARCHIVED + ", T." + TASK_LATITUDE + ", T." + TASK_LONGITUDE + ", T." + TASK_CATEGORY_ID +
                " FROM " + TABLE_TASKS + " T" +
                " INNER JOIN " + TABLE_CATEGORIES + " C" +
                " ON T." + TASK_CATEGORY_ID + " = C." + CATEGORY_ID +
                " WHERE C." + CATEGORY_USER_ID + " = '" + UserId + "'" +
                " AND T." + TASK_WHEN + " = '" + taskWhen + "'" +
                " AND T." + TASK_ARCHIVED + "=0;", null);

        if (!cursor.moveToFirst()) return null;

        if (cursor.moveToFirst()) {

            do {

                boolean checked;
                checked = cursor.getString(3).equals("1");

                boolean archived;
                archived = cursor.getString(4).equals("1");

                Task task = new Task(
                        cursor.getString(1),
                        cursor.getString(2),
                        checked,
                        Integer.parseInt(cursor.getString(7)));

                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setArchived(archived);
                if (cursor.getString(5) != null && cursor.getString(6) != null) {
                    Double latitude = Double.parseDouble(cursor.getString(5));
                    Double longitude = Double.parseDouble(cursor.getString(6));
                    task.setCoords(new LatLng(latitude, longitude));
                }
                tasksList.add(task);
            } while (cursor.moveToNext());


        }

        return tasksList;
    }

    /**
     * Updates the information of the task passed as argument in the data base
     *
     * @param task task to be updated
     */
    public void updateTask(Task task) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        int checked = (task.isComplete()) ? 1 : 0;
        int archived = (task.isArchived()) ? 1 : 0;

        values.put(TASK_NAME, task.getText());
        values.put(TASK_WHEN, task.getDate());
        values.put(TASK_CHECKED, checked);
        values.put(TASK_ARCHIVED, archived);
        if (task.getCoords() != null) {
            values.put(TASK_LATITUDE, String.valueOf(task.getCoords().latitude));
            values.put(TASK_LONGITUDE, String.valueOf(task.getCoords().longitude));
        }
        values.put(TASK_CATEGORY_ID, task.getCategoryId());

        db.update(TABLE_TASKS, values, TASK_ID + "=?", new String[]{String.valueOf(task.getId())});

        db.close();
    }

    /**
     * Deletes the task passed as argument of the data base
     *
     * @param task task to be deleted
     */
    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, TASK_ID + "=" + task.getId(), null);
        db.close();
    }

    /**
     * Deletes all tasks in the data base
     */
    public void deleteAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, null, null);
        db.close();
    }

}
