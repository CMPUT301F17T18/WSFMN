package com.wsfmn.controller;

import com.wsfmn.model.ProfileName;

import java.util.concurrent.ExecutionException;

/**
 * Created by Fredric on 2017-11-11.
 * Updated by nmayne on 2017-11-28.
 */

public class ProfileNameController {
    private static ProfileNameController INSTANCE = null;
    private static ProfileName profileName = null;


    private ProfileNameController() {
        profileName = new ProfileName();
        init();
    }

    /**
     * Instantiate the profile attribute.
     * This pulls the data from the locally saved ProfileName via OfflineController.
     */
    public static ProfileNameController getInstance() {
        if(INSTANCE == null){
            INSTANCE = new ProfileNameController();
        }
        return INSTANCE;
    }

    public static String getProfileName() {
        return profileName.getName();
    }

    public static void setProfileName(String name) {
        profileName.setName(name);
    }

    public static String getProfileID() {
        return profileName.getId();
    }

    public static void setProfileID(String id) {
        profileName.setId(id);
    }



    public static void storeProfileNameOffline(ProfileName profileName) {
        OfflineController.StoreUserProfile storeUserProfile=
                new OfflineController.StoreUserProfile();

        storeUserProfile.execute(profileName);

        App.reinitialize();

    }

    private static void init() {
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
