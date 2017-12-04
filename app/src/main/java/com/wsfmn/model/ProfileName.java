package com.wsfmn.model;

import java.util.Comparator;

import io.searchbox.annotations.JestId;

/**
 * Created by Fredric on 2017-11-05.
 */

/**
 * a Class for the User's profilename
 */
public class ProfileName {
    private String name;

    @JestId
    private String id;

    private int score;

    /**
     * Empty construct if new ProfileName is called without parameters.
     */
    public ProfileName(){
        this.name = "";
    }

    /**
     * Construct for new ProfileName with name being param name
     * @param name
     */
    public ProfileName(String name) {
        this.name = name;
    }

    /**
     * Method to receive name from ProfileName
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set the name for ProfileName
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to receive ID from ProfileName.
     * Main use for ElasticSearch
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Method to set the ID for ProfileName
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }





}



