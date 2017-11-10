package com.wsfmn.habit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicholasmayne on 2017-10-16.
 */


/**
 * A class to store the HabitEvents of a particular Habit.
 * History refers to the list of all past HabitEvents.
 */
public class HabitHistory {
    private ArrayList<HabitEvent> habitHistory;


    /**
     * Construct a new Habit History to store Habit Events.
     */
    public HabitHistory(){
        this.habitHistory = new ArrayList<HabitEvent>();
    }

    /**
     * Checks it the HabitHistory is empty
     * @return Boolean true if HabitHistory is empty
     */
    public Boolean isEmpty() {
        return habitHistory.isEmpty();
    }

    /**
     * Appends a HabitEvent to the end of HabitHistory
     * @param habitEvent HabitEvent: HabitEvent to add to HabitHistory
     */
    public void add(HabitEvent habitEvent) {
        habitHistory.add(habitEvent);
    }

    /**
     *
     * @param index int: the index of the HabitEvent to get
     * @return HabitEvent at the specified index
     * @throws IndexOutOfBoundsException
     */
    public HabitEvent get(int index) throws IndexOutOfBoundsException {
            return habitHistory.get(index);
    }

    /**
     * Removes and returns a HabitEvent at the specified index in HabitHistory,
     * and decrements the HabitHistory indices that follow it.
     * @param index int: the index of the HabitEvent to remove
     * @return HabitEvent removed from the specified index
     * @throws IndexOutOfBoundsException
     */
    public HabitEvent remove(int index) throws IndexOutOfBoundsException{
        return habitHistory.remove(index);
    }

    /**
     * Check to see it a HabitEvent is in HabitHistory
     * @param habitEvent HabitEvent: check HabitHistory for this HabitEvent
     * @return Boolean true if the HabitEvent is in HabitHistory
     */
    public Boolean contains(HabitEvent habitEvent) {
        return habitHistory.contains(habitEvent);
    }

    /**
     * Get the first index of the specified HabitEvent, if it is in HabitHistory
     * @param habitEvent HabitEvent: get the first index of this HabitEvent
     * @return int first index of the specified HabitEvent
     */
    public int indexOf(HabitEvent habitEvent){
        return habitHistory.indexOf(habitEvent);
    }

    /**
     * Adds a full list of HabitEvents to the HabitHistory
     * @param habitEvents
     */
    public void addAllHabitEvents(List<HabitEvent> habitEvents) {
        this.habitHistory.addAll(habitEvents);
    }

    /**
     * Get the size of the habit history
     * @return int the number of entries in HabitHistory
     */
    public int size() {
        return habitHistory.size();
    }


}
