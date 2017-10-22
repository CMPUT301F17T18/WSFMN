package com.wsfmn.habittracker;

import java.util.ArrayList;

/**
 * Created by Fredric on 2017-10-16.
 */

public class Profile {


    private ArrayList<Request> requestList = new ArrayList<Request>();

    private String Name;


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


}
