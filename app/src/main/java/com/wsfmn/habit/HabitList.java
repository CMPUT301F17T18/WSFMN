package com.wsfmn.habit;

import com.wsfmn.habitcontroller.OnlineController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Fredric on 2017-10-21.
 */

public class HabitList {
    private ArrayList<Habit> habits;


    public HabitList() {
        this.habits = new ArrayList<Habit>();
    }

    public HabitList(ArrayList<Habit> habits) {
        this.habits = habits;
    }

    // add Habit to HabitList
    public void addHabit(Habit habit){
        habits.add(habit);
    }

    // delete the Habit from HabitList
    public void deleteHabit(Habit habit){
        habits.remove(habit);
    }

    public void deleteHabitAt(int index){
        habits.remove(index);
    }

    // TODO nmayne: added this, needs a test in TestHabitList
    public int getSize() {
        return habits.size();
    }

    // TODO nmayne: added this, needs a test in TestHabitList
    public void addAllHabits(List<Habit> habitsToAdd) {
        habits.addAll(habitsToAdd);
    }

    public Habit getHabit(int index){
        return habits.get(index);
    }

    public void setHabit(int index, Habit habit){
        habits.set(index, habit);
    }

    public boolean hasHabit(Habit habit){
        return habits.contains(habit);
    }

    // TODO alsobaie: added this, needs testing
    public ArrayList<Habit> getHabitList(){
        return  habits;
    }

    public ArrayList<Habit> getHabitsForToday(){
        ArrayList<Habit> habitsForToday = new ArrayList<Habit>();

        Date today = new Date();
        int dayOfWeek = today.getDayOfWeek();

        for(int i = 0; i < habits.size(); i++){
            int com = habits.get(i).getDate().compareDate(today);

            if(com == -1 || com == 0){
                if(habits.get(i).getWeekDays().getDay(dayOfWeek-1))
                    habitsForToday.add(habits.get(i));
            }
        }
        return habitsForToday;
    }

}