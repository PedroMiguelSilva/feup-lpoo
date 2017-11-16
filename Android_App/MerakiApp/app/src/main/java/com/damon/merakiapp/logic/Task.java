package com.damon.merakiapp.logic;

import com.google.android.gms.maps.model.LatLng;

public class Task {
    /**
     * Task's id
     */
    private int id;

    /**
     * Task's name
     */
    private String text;

    /**
     * Task's day
     */
    private String date;

    /**
     * True if the task is finished
     */
    private boolean complete;

    /**
     * Tasks coordinates
     */
    private LatLng coords;

    /**
     * True if the task is archived
     */
    private boolean archived;

    /**
     * Id of task's category
     */
    private int categoryId;

    /**
     * Task constructor
     *
     * @param text       task's name
     * @param date       task's day
     * @param complete   true if the task is finished, false otherwise
     * @param categoryId id of task's catgory
     */
    public Task(String text, String date, boolean complete, int categoryId) {
        this.id = -1;
        this.text = text;
        this.date = date;
        this.complete = complete;
        this.categoryId = categoryId;
        this.archived = false;
    }

    /**
     * Returns task's id
     *
     * @return task's id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets tasks's id
     *
     * @param id id to be setted
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns task's name
     *
     * @return task's name
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets task's name
     *
     * @param name name to be setted
     */
    public void setText(String name) {
        this.text = name;
    }

    /**
     * Returns task's day
     *
     * @return task's day
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Sets task's date
     *
     * @param date date to be setted
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Checks if task is completed
     *
     * @return true if task is completed, false otherwise
     */
    public boolean isComplete() {
        return this.complete;
    }

    /**
     * Sets complete
     *
     * @param complete true if the task is finished, false otherwise
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * Returns id of task's category
     *
     * @return id of task's category
     */
    public int getCategoryId() {
        return this.categoryId;
    }

    /**
     * Sets id of task's category
     *
     * @param id id to be setted
     */
    public void setCategoryId(int id) {
        this.categoryId = id;
    }

    /**
     * Returns task's coordinates
     *
     * @return task's coordinates
     */
    public LatLng getCoords() {
        return coords;
    }

    /**
     * Sets taks's coordinates
     *
     * @param coords coordinates to be setted
     */
    public void setCoords(LatLng coords) {
        this.coords = coords;
    }

    /**
     * Checks if task is archived
     *
     * @return true if task is archived, false otherwise
     */
    public boolean isArchived() {
        return archived;
    }

    /**
     * Sets archived
     *
     * @param archived true if the task is finished, false otherwise
     */
    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}


