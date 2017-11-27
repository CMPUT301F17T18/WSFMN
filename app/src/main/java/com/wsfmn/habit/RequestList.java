package com.wsfmn.habit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredric on 2017-11-03.
 */

public class RequestList {
    private ArrayList<Request> requests;
    public RequestList() {
        this.requests = new ArrayList<Request>();
    }

    public RequestList(ArrayList<Request> requests) {
        this.requests = requests;
    }

    public void addRequst(Request request){
        requests.add(request);
    }

    public void deleteRequest(Request request){
        requests.remove(request);
    }

    public void deleteRequestAt(int index){
        requests.remove(index);
    }

    public int size() {
        return requests.size();
    }

    public Request get(int index) throws IndexOutOfBoundsException {
        return requests.get(index);
    }

    public Request remove(int index) throws IndexOutOfBoundsException{
        return requests.remove(index);
    }


    public void addAllRequests(List<Request> requestsToAdd) {
        requests.addAll(requestsToAdd);
    }

    public Request getRequest(int index){
        return requests.get(index);
    }

    public void setRequest(int index, Request request){
        requests.set(index, request);
    }

    public boolean hasRequest(Request request){
        return requests.contains(request);
    }

}
