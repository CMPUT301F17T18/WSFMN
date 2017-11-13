package com.wsfmn.habitcontroller;

import com.wsfmn.habit.ProfileName;

import java.util.concurrent.ExecutionException;

/**
 * Created by Fredric on 2017-11-11.
 */

public class ProfileController {
    private static ProfileController INSTANCE = new ProfileController();


    /**
     * Instantiate the habitHistory attribute.
     * This pulls the data from the locally saved HabitHistory via OfflineController.
     */
    public static ProfileController getInstance() {
        if(INSTANCE == null){
            INSTANCE = new ProfileController();
        }
        return INSTANCE;
    }

}
