package com.wsfmn.model;

import com.wsfmn.controller.HabitHistoryController;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class HabitList {
    private ArrayList<Habit> habits;

    /**
     *
     */
    public HabitList() {
        this.habits = new ArrayList<Habit>();
    }

    /**
     *
     * @param habits
     */
    public HabitList(ArrayList<Habit> habits) {
        this.habits = habits;
    }

    /**
     *
     * @param habit
     */
    public void addHabit(Habit habit){
        habit.setSearchTitle();
        habits.add(habit);
    }

    /**
     *
     * @param habit
     */
    public void deleteHabit(Habit habit){
        habits.remove(habit);
    }

    /**
     *
     * @param index
     */
    public void deleteHabitAt(int index){
        habits.remove(index);
    }

    /**
     *
     * @return
     */
    public int size() {
        return habits.size();
    }

    /**
     *
     * @param habitsToAdd
     */
    public void addAllHabits(List<Habit> habitsToAdd) {
        for(Habit h: habitsToAdd) {
            h.setSearchTitle();
        }
        habits.addAll(habitsToAdd);
    }

    /**
     *
     * @param index
     * @return
     */
    public Habit getHabit(int index){
        return habits.get(index);
    }

    /**
     *
     * @param index
     * @param habit
     */
    public void setHabit(int index, Habit habit){
        habit.setSearchTitle();
        habits.set(index, habit);
    }

    /**
     *
     * @param habit
     * @return
     */
    public boolean hasHabit(Habit habit){
        return habits.contains(habit);
    }

    // TODO alsobaie: added this, needs testing

    /**
     *
     * @return
     */
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