package com.wsfmn.habittracker;

import java.util.ArrayList;

/**
 * Created by Fredric on 2017-10-21.
 */

public class HabitList extends ArrayList{
    private ArrayList<Habit> habits;

    public HabitList() {
        this.habits = new ArrayList<Habit>();
    }
    public HabitList(ArrayList<Habit> habits) {
        this.habits = habits;
    }

    // add Habit to a list of Habits
    public void addHabit(Habit habit){
        habits.add(habit);
    }

    // delete the Habit from a lsit of Habits
    public void deleteHabit(Habit habit){
        habits.remove(habit);

    }


    public Habit getHabit(int index){
        return habits.get(index);
    }


    public boolean hasHabit(Habit habit){
        return habits.contains(habit);
    }


}
