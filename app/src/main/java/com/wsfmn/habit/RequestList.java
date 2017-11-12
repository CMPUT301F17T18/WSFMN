package com.wsfmn.habit;

import java.util.ArrayList;

/**
 * Created by Fredric on 2017-11-03.
 */

public class RequestList {
    private ArrayList<Request> requests = new ArrayList<Request>();

    public void addTweet(Request request){
        requests.add(request);
    }


    public void deleteTweet(Request request){
        requests.remove(request);

    }

    public boolean hasTweet(Request request){
        return requests.contains(request);
    }

    public Request getTweet(int index){
        return requests.get(index);
    }

}
