package com.wsfmn.habit;

/**
 * Created by musaed on 2017-11-11.
 */

public class Plan {

    /**
     *  Habit Creation:
     *      -   oldDate is set to null
     *      -   currDate is set to null indicating no change in plan
     *
     */


            /**
             *  Conditions:
             *      -   A Habit object cannot use any methods of a Date object
             *      -   A Habit object cannot use any methods of a WeekDays objects.
             *      -   A Habit object cannot use any getters and setters for Date objects.
             *      -   A Habit object cannot use getters and setters for WeekDays objects.
             *
             *
             *  If a specific functionality is needed, make the getters and setters private
             *  and write the appropriate functions to call them and provide the needed result.
             *
             *
             *
             *
             */

    /**
     *  Methods that can alter a Habit's plan:
     *      -   setDay
     *      -   setYear
     *      -   setMonth
     *      -   setDay
     *      -   unsetDay
     *
     *  Develop proper behavior for these methods
     *
     *  -   To change a date, use
     *
     */


    /**
     *  when calculating totalOcc, we care only about the last date plan was updated
     */


    Date oldDate;
    Date currDate;

    /**
     *  On creation, we have curr = date
     *  hasChanged = false;
     *
     *  Date is future:
     *      -   return count
     *
     *  Date is past:
     *      -   plan has not changed
     *          start counting from curr to today, no adding
     *
     *      -   plan changed
     *
     *          -   schedule changed:
     *              -   set curr = date it was changed
     *              -   start counting from curr, adding to prev
     *              -   hasChanged = false
     *
     *          -   Date is changed:
     *              -   set curr = new date
     *              -   count from curr, adding to prev
     *              -   hasChanged = false;
     *
     *  identify plan has changed: keep stats about old WeekDays to compare with curr
     *                             or compare curr with date
     *
     */

    /**
     *  gets updated if schedule changes:
     *      and we count from updatedPlanDate
     *
     *  if data is changes:
     *      -   if date is set to future
     */


    /*
    Date date;
    WeekDays oldWeekDays;
    WeekDays weekDays;
    int oldOcc = 0;
    int occ = 0;
    int tempOcc = 0;
    boolean hasChanged = false;

    public void totalPossibleOccurrences(){

        if(!planChanged()){
            Date today = new Date();
            tempOcc = totalOcc(currDate, today);
            occ = tempOcc + oldOcc;
        }

        else{
            oldOcc = occ;
            tempOcc = totalOcc(currDate, new Date());
            occ = oldOcc + tempOcc;
            hasChanged = false;
        }
    }

    //calculate from beg to end
    public int totalOcc(Date start, Date end){
        return 0;
    }

   public void changeDay(int day){

       //   plan has changed
       if(date.getDay() != day){
           oldDate = this.date;
           currDate = new Date();
           this.date.setDay(day);
       }
   }

    public void changeMonth(int month){

        //   plan has changed
        if(date.getMonth() != month){
            oldDate = this.date;
            currDate = new Date();
            this.date.setDay(month);
        }
    }

    public void changeYear(int year){

        //   plan has changed
        if(date.getYear() != year){
            oldDate = this.date;
            currDate = new Date();
            this.date.setDay(year);
        }
    }

*/
    /**
     *  if a schedule is changed, we need to know new
     *
     */
    /*
    public void setDay(int day){

        //  plan has changed
        if(!weekDays.getDay(day)){
            currDate = new Date();
            oldDate = this.date;
            oldWeekDays = weekDays.copy();
            weekDays.setDay(day);
        }
    }

    public void unsetDay(int day){

        //  plan has changed
        if(!weekDays.getDay(day)){
            currDate = new Date();
            oldDate = this.date;
            oldWeekDays = weekDays.copy();
            weekDays.unsetDay(day);
        }
    }


    public void setDate(Date date){

        //  plan has changed
        if(this.date.compareDate(date) < 0){
            oldDate = this.date;
            currDate = date;
            this.date = date;
        }
    }

    public boolean planChanged(){

        //  plan was just created
        if(currDate == null)
            return false;

        return date.compareDate(currDate) != 0;
    }


    //  should be removed
    public void setWeekDays(WeekDays weekDays){

    }
    */
}
