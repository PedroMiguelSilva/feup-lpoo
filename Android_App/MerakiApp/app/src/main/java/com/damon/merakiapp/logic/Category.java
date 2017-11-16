package com.damon.merakiapp.logic;

public class Category {

    /**
     * Category's id
     */
    private int id;

    /**
     * Category's name
     */
    private String name;

    /**
     * Id of the category's owner
     */
    private int userId;

    /**
     * Category constructor
     *
     * @param name   category's name
     * @param userId if of the category's owner
     */
    public Category(String name, int userId) {
        this.id = -1;
        this.name = name;
        this.userId = userId;
    }

    /**
     * Returns category's id
     *
     * @return catgory's id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets category's id
     *
     * @param id id to be setted
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns category's name
     *
     * @return category's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets category's name
     *
     * @param name name to be setted
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Returns the id of the category's owner
     *
     * @return id of the category's owner
     */
    public int getUserId() {
        return userId;
    }
}
