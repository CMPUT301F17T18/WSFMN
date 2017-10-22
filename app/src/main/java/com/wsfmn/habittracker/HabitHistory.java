package com.wsfmn.habittracker;

import java.util.ArrayList;

/**
 * Created by nicholasmayne on 2017-10-16.
 */

public class HabitHistory {

    private ArrayList<HabitEvent> habitHistory = new ArrayList<HabitEvent>();


    public HabitEvent getHabitEventAt(int index) {
        return habitHistory.get(index);
    }

    public void addHabitEvent(HabitEvent habitEvent) {
        habitHistory.add(habitEvent);
    }
}
