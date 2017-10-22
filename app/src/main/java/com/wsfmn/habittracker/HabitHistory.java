package com.wsfmn.habittracker;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nicholasmayne on 2017-10-16.
 */

public class HabitHistory {

    private ArrayList<HabitEvent> habitHistory = new ArrayList<HabitEvent>();



    public void add(HabitEvent habitEvent) {
        habitHistory.add(habitEvent);
    }

    public HabitEvent getHabitEventAt(int index) {
        if (habitHistory.isEmpty()) {
            return null;
        } else if (index >= habitHistory.size()){
            return null;
        } else {
            return habitHistory.get(index);
        }
    }

    public int remove

}
