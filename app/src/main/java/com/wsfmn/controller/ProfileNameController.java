/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.controller;

import com.wsfmn.model.ProfileName;

import java.util.concurrent.ExecutionException;

/**
 * A singleton controller to manage the user's ProfileName.
 *
 */
public class ProfileNameController {
    private static ProfileNameController INSTANCE = null;
    private static ProfileName profileName = null;

    /**
     * Construct a new ProfileNameController.
     */
    private ProfileNameController() {
        profileName = new ProfileName();
        init();
    }

    /**
     * Instantiate the profile attribute.
     * This pulls the data from the locally saved ProfileName via OfflineController.
     *
     */
    public static ProfileNameController getInstance() {
        if(INSTANCE == null){
            INSTANCE = new ProfileNameController();
        }
        return INSTANCE;
    }

    /**
     * Get the username.
     *
     * @return the user's username
     *
     */
    public String getProfileName() {
        return profileName.getName();
    }

    /**
     * Set the username.
     * @param name the user's username.
     */
    public void setProfileName(String name) {
        profileName.setName(name);
    }

    /**
     * Get the profile ID.
     *
     * @return the profile ID
     */
    public String getProfileID() {
        return profileName.getId();
    }

    /**
     * Set the profile ID.
     *
     * @param id the profile ID
     */
    public void setProfileID(String id) {
        profileName.setId(id);
    }

    /**
     * Update the user's score.
     *
     */
    public void updateScore() {
        double score_temp = 0;
        HabitListController c = HabitListController.getInstance();
        for (int i = 0; i < c.size(); i++) {
            score_temp = score_temp + c.getHabit(i).getScore();
        }
        if (c.size() > 0) {
            profileName.setScore((int) score_temp/c.size());
        } else {
            profileName.setScore(0);
        }
        storeNewProfileNameOffline(profileName);
        OnlineController.StoreNameInDataBase online = new OnlineController.StoreNameInDataBase();
        online.execute(profileName);
    }

    /**
     * Get the user's score.
     *
     * @return user's score
     */
    public int getScore() {
        return profileName.getScore();
    }

    /**
     * Store this profilename Offline.
     *
     * @param pn the profileName to store.
     */
    public void storeNewProfileNameOffline(ProfileName pn) {
        profileName = pn;
        OfflineController.StoreUserProfile storeUserProfile=
                new OfflineController.StoreUserProfile();

        storeUserProfile.execute(profileName);
        App.reinitialize();

    }

    /**
     * Initialize the ProfileName from the stored data.
     */
    private void init() {
        OfflineController.GetUserProfile getUserProfile =
                new OfflineController.GetUserProfile();

        getUserProfile.execute();
        try {
            profileName = getUserProfile.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
