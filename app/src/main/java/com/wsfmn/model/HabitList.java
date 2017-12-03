package com.wsfmn.model;

import com.wsfmn.controller.HabitHistoryController;

import java.util.ArrayList;
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

    public int size() {
        return habits.size();
    }

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
        ArrayList<Habit> hl = new ArrayList<Habit>();
        boolean flag = true;

        Date today = new Date();
        int dayOfWeek = today.getDayOfWeek();

        for(int i = 0; i < habits.size(); i++){
            int com = habits.get(i).getDate().compareDate(today);

            if(com == -1 || com == 0){
                if(habits.get(i).getWeekDays().getDay(dayOfWeek-1))
                    hl.add(habits.get(i));
            }
        }

        HabitHistoryController c = HabitHistoryController.getInstance();
        for (int i = 0; i < hl.size(); i++) {
            flag = true;
            for (int j = 0; j < c.size(); j++) {
                if (c.get(j).getHabit().equal(hl.get(i))
                        && c.get(j).getDate().compareDate(new Date()) == 0)
                    flag = false;
            }

            if(flag)
                habitsForToday.add(hl.get(i));
        }

        return habitsForToday;
    }

}