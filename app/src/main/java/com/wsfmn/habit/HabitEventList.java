package com.wsfmn.habit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siddhant on 2017-11-09.
 */

public class HabitEventList {

    private ArrayList<HabitEvent> habitEvent;

    //Creating new habit Event
    public HabitEventList(){
        this.habitEvent = new ArrayList<HabitEvent>();
    }

    public HabitEventList(ArrayList<HabitEvent> habitEventInstance){
        this.habitEvent = habitEventInstance;
    }

    public void addHabitEvent(HabitEvent habitEventInstance){
        habitEvent.add(habitEventInstance);
    }

    public void deleteHabitEvent(HabitEvent habitEventInstance){
        habitEvent.remove(habitEventInstance);
    }

    public void deleteHabitEventAt(int index){
        habitEvent.remove(index);
    }

    public int getSize() {
        return habitEvent.size();
    }

    public void addAllHabitEvent(List<HabitEvent> habitEventToAdd) {
        habitEvent.addAll(habitEventToAdd);
    }

    public HabitEvent getHabitEvent(int index){
        return habitEvent.get(index);
    }

    public void setHabitEvent(int index, HabitEvent habitEventInstance){
        habitEvent.set(index, habitEventInstance);
    }

    public boolean hasHabitEvent(HabitEvent habitEventInstance){
        return habitEvent.contains(habitEventInstance);
    }

    public ArrayList<HabitEvent> getHabitList(){
        return  habitEvent;
    }

}
