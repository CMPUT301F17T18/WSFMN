package com.wsfmn.habit;


import java.util.Calendar;

/**
 * Created by musaed on 2017-10-16.
 */

public class Habit{

    private String id;
    private String title;
    private String reason;
    private Date date;
    private WeekDays weekDays;


    public Habit(String title, Date date) throws HabitTitleTooLongException,
                                                DateNotValidException {
        this.id = null;
        this.setDate(date);
        this.setTitle(title);
    }

    public Habit(String title, String reason, Date date) throws HabitTitleTooLongException,
                                                            HabitReasonTooLongException,
                                                            DateNotValidException{
        this(title, date);
        this.setReason(reason);
    }

    public Habit(String title, String reason, Date date, WeekDays weekDays) throws HabitTitleTooLongException,
                                                            HabitReasonTooLongException,
                                                            DateNotValidException{
        this(title, reason, date);
        this.weekDays = weekDays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws HabitTitleTooLongException{
        if(title.length() < 1 ||title.length() > 20){
            throw new HabitTitleTooLongException();
        }
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) throws HabitReasonTooLongException{
        if(reason.length() > 30){
            throw new HabitReasonTooLongException();
        }
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date toStart) throws DateNotValidException {
        if(toStart.compareDate(new Date()) == -1)
            throw new DateNotValidException();
        this.date = toStart;
    }

    public WeekDays getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(WeekDays weekDays) {
        this.weekDays = weekDays;
    }

    public int getHabitOccurrencePercentage(){
        Calendar c = Calendar.getInstance();
        int totalPossibleOccurrence = 0;
        int totalOccurred = 0;

        Date today = new Date();

        int weeks = today.getDay() / 7;
        int d = today.getDay() % 7;

        int tm = today.getMonth();
        int dm = this.date.getMonth();



        for(int i = 0; i <= tm - dm; i++){
            int beg;
            int dayOfWeek;
            int end;

            if(i == tm - dm){   //  current month of habit

                if(dm == tm){
                    beg = this.date.getDay();
                }
                else{
                    beg = 1;
                }

                c.setFirstDayOfWeek(Calendar.MONDAY);
                Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                c.set(Calendar.MONTH, dm + i);


                end = c.getActualMaximum(Calendar.DATE);
            }
            else{
                int dd = 0;

            }
        }


        return totalPossibleOccurrence;
        //return (totalOccurred / totalPossibleOccurrence) * 100;
    }

    /**
     *  beg: day in month
     *  dayOfWeek: dayOfWeek beg corresponds to
     *  end: end of day in month
     */
    public int caldays(int beg, int dayOfWeek, int end){
        int total = 0;
        //simple for loop
        return total;
    }

    // nmayne: A local key for a habit, as a combination of title and date... but this
    // is dependent upon Date, which is an issue... probably a better way to do this
    @Override
    public String toString(){
        return title + "    " + date;
    }
}
