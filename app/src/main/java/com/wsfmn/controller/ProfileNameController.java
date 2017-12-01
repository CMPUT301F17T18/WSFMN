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

    public String getProfileName() {
        return profileName.getName();
    }

    public void setProfileName(String name) {
        profileName.setName(name);
    }

    public String getProfileID() {
        return profileName.getId();
    }

    public void setProfileID(String id) {
        profileName.setId(id);
    }

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

    public int getScore() {
        return profileName.getScore();
    }

    public void storeNewProfileNameOffline(ProfileName pn) {
        profileName = pn;
        OfflineController.StoreUserProfile storeUserProfile=
                new OfflineController.StoreUserProfile();

        storeUserProfile.execute(profileName);
        App.reinitialize();

    }

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
