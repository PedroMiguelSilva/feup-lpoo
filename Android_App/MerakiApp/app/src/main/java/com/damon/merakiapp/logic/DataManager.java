package com.damon.merakiapp.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DataManager {

    /**
     * Singleton instance
     */
    static private DataManager instance = null;


    /**
     * Data base
     */
    private DataBase dataBase;

    /**
     * User logged in
     */
    private User userLogged;

    /**
     * Application context
     */
    private Context context;


    /**
     * Data Manager constructor
     */
    private DataManager() {
    }

    /**
     * Gets singleton instance
     *
     * @return singleton instance
     */
    static public DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    /**
     * Sets application context
     *
     * @param context context to be setted
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Creates data base
     */
    public void createDataBase() {
        this.dataBase = new DataBase(this.context);
    }

    /**
     * Returns data base
     *
     * @return data base
     */
    public DataBase getDataBase() {
        return this.dataBase;
    }

    /**
     * Returns user currently logged in
     *
     * @return user currently logged in
     */
    public User getUserLogged() {
        return this.userLogged;
    }

    /**
     * Adds category default if the user doesn't have categories
     */
    private void initCategories() {
        if (this.dataBase.getUserCategories(userLogged.getId()) == null) {
            Category Default = new Category("Default", userLogged.getId());
            this.dataBase.addCategory(Default);
        }
    }

    /**
     * Initializes android preferences with id of the user logged in as -1
     */
    public void initPreferences() {
        if (getPreferences() != -1) {
            dataBase.setUserLoggedId(getPreferences());
            this.userLogged = dataBase.getUser(dataBase.getUserLoggedId());
        }
    }

    /**
     * Saves a new id in the android preferences
     *
     * @param id id to be saved
     */
    private void savePreferences(int id) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = PreferenceManager.getDefaultSharedPreferences(this.context);
        editor = settings.edit();
        editor.putInt("USER LOGGED", id);

        editor.commit();
    }

    /**
     * Returns the id in the android preferences
     *
     * @return id in the android preferences
     */
    public int getPreferences() {
        SharedPreferences settings;
        int id;

        settings = PreferenceManager.getDefaultSharedPreferences(this.context);
        id = settings.getInt("USER LOGGED", -1);

        return id;
    }

    /**
     * Verifies if the user is in the data base.
     * If it is sets preferences and userlogged in and returns true,
     * if it's not returns false.
     * In case of a google user if login wasn't successful, calls register.
     *
     * @param user user to try login
     * @return true in case of success, false otherwise
     */
    public boolean login(User user) {


        if (!dataBase.login(user.getEmail(), user.getPassword())) {

            if (user.isGoogleUser()) {
                boolean res = register(user);
                if (!res) return false;
            } else return false;
        } else {
            savePreferences(dataBase.getUserLoggedId());
            this.userLogged = dataBase.getUser(dataBase.getUserLoggedId());
        }
        initCategories();
        return true;
    }

    /**
     * Tries to add a new user in data base.
     * If it wasn't successful returns false,
     * if it was calls login.
     *
     * @param user user
     * @return true in case of success, false otherwise
     */
    public boolean register(User user) {
        if (dataBase.addUser(user) == -1)
            return false;
        else {
            login(user);
            return true;
        }
    }

    /**
     * Sets android preferences as -1 and user logged as null
     */
    public void logout() {
        savePreferences(-1);
        dataBase.setUserLoggedId(-1);
        userLogged = null;
    }
}
