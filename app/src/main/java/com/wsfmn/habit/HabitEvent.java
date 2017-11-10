package com.wsfmn.habit;

/**
 * Created by skhanna1 on 10/16/17.
 */

public class HabitEvent{
    private String id;
    private Habit habit;
    private String comment;
    private Date date;
    private Boolean done;
    //change by wei, change location parts
    private Geolocation location;
    //Will change to appropriate Data Type when implement it(ImageView).
    String pic;

    public HabitEvent(Habit habit, Date date, Boolean done, String comment) throws HabitCommentTooLongException {
        this.habit = habit;
        this.date = date;
        this.done = done;
        this.setComment(comment);
    }


    public Habit getHabitType() {
        return habit;
    }

    public void setHabitType(Habit habitType) {
        this.habit = habitType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) throws HabitCommentTooLongException {
        if(comment.length() > 20){
            throw new HabitCommentTooLongException();
        }
        this.comment = comment;
    }


    public Date getDate(){return date;}

    public void setDate(Date date){this.date = date;}


    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getDone() {
        return done;
    }

    public void setLocation(Geolocation location){
        this.location = location;
    }

    public Geolocation getLocation() {return this.location;}


    public String getPic(){return this.pic;}

    //public void location(){this.location = 5;}

    public void AddPic(){this.pic = "Image";}

    public void updateHabitHistory(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
