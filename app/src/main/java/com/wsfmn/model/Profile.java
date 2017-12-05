/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.model;

import java.util.ArrayList;

/**
 * Created by Fredric on 2017-10-16.
 */

public class Profile {


    private ArrayList<Request> requestList = new ArrayList<Request>();

    private String Name = "";

    private int score;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void followUser(String participantName){
        // WILL BE SENDING TO ANOTHER USER'S PHONE BUT FOR NOW WILL JUST BE SENDING
        // TO OWN PHONE
        Request request = new Request(participantName);
        addRequest(request);
    }

    public void shareWithUser(String participantName){
        Request request = new Request(participantName);
        addRequest(request);

    }

    // put requests in list
    public void addRequest(Request request){
        requestList.add(request);
    }

    // decline request do nothing else.
    public void declineRequest(Request request){
        requestList.remove(request);

    }

    // accept request and
    public void acceptRequest(Request request){
        requestList.remove(request);
        // give info/ receive info from other participant
    }

    public boolean hasRequest(Request request){
        return requestList.contains(request);
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
