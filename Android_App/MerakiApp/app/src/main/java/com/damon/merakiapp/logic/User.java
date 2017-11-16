package com.damon.merakiapp.logic;

import android.graphics.Bitmap;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private boolean GoogleUser;
    private String photoUrl;
    private Bitmap bitmap;

    /**
     * User constructor
     *
     * @param name       user's name
     * @param email      user's email
     * @param password   user's password
     * @param GoogleUser true if the user logged in with a google account
     */
    public User(String name, String email, String password, boolean GoogleUser) {
        this.id = -1;
        this.name = name;
        this.email = email;
        this.password = password;
        this.GoogleUser = GoogleUser;
        this.photoUrl = " ";
    }

    /**
     * Return user's id
     *
     * @return user's id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets user's id
     *
     * @param id id to be setted
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns user's name
     *
     * @return user's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets user's name
     *
     * @param name name to be setted
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns user's email
     *
     * @return user's email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets user's email
     *
     * @param email email to be setted
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns user's password
     *
     * @return user's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets user's password
     *
     * @param password password to be setted
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the user logged in with a google account
     *
     * @return true if it's a google user, false otherwise
     */
    public boolean isGoogleUser() {
        return GoogleUser;
    }

    /**
     * Returns user's profile picture url
     *
     * @return user's profile picture url
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * Returns user's profile picture url
     *
     * @param photoUrl user's profile picture url
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * Returns user's bitmap picture
     *
     * @return user's bitmap picture
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Sets user's bitmap picture
     *
     * @param bitmap
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
