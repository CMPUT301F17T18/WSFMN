package com.wsfmn.habit;

/**
 * Created by Fredric on 2017-11-27.
 */

public class Friend extends ProfileName {

    private int score;

    public Friend(String name) {
        super(name);
    }

    public Friend(String name, int score) {
        super(name);
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int add){
        this.score = this.score + add;
    }

    public void subScore(int sub){
        this.score = this.score - sub;
    }

}
