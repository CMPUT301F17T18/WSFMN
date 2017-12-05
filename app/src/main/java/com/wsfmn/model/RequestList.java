/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredric on 2017-11-03.
 */

/**
 * Custom list for requestlist
 */
public class RequestList {
    private ArrayList<Request> requests; //Array for requestlist

    /**
     * Constructor for the list
     */
    public RequestList() {
        this.requests = new ArrayList<Request>();
    }


    public RequestList(ArrayList<Request> requests) {
        this.requests = requests;
    }

    /**
     * adding request to the list.
     * @param request
     */
    public void addRequst(Request request){
        requests.add(request);
    }

    /**
     * delete a request from the list.
     * @param request
     */
    public void deleteRequest(Request request){
        requests.remove(request);
    }

    /**
     * delete a request at a location
     * @param index
     */
    public void deleteRequestAt(int index){
        requests.remove(index);
    }

    /**
     * return the size of the list.
     * @return
     */
    public int size() {
        return requests.size();
    }

    /**
     *  get a request at certain location in the list.
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    public Request get(int index) throws IndexOutOfBoundsException {
        return requests.get(index);
    }

    /**
     * remove a a certain location
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    public Request remove(int index) throws IndexOutOfBoundsException{
        return requests.remove(index);
    }

    /**
     * Add all requests to the list from a list.
     * @param requestsToAdd
     */
    public void addAllRequests(List<Request> requestsToAdd) {
        requests.addAll(requestsToAdd);
    }

    /**
     * get a request from certain index.
     */
    public Request getRequest(int index){
        return requests.get(index);
    }

    /**
     * set a request at an index.
     * @param index
     * @param request
     */
    public void setRequest(int index, Request request){
        requests.set(index, request);
    }

    /**
     * check if the list has a certain request.
     * @param request
     * @return
     */
    public boolean hasRequest(Request request){
        return requests.contains(request);
    }

}
