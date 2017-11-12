package com.wsfmn.habit;

import io.searchbox.annotations.JestId;
/**
 * Created by Fredric on 2017-10-21.
 */

public class Request extends ProfileName{
    private String searchName;
    private String requestType;

    public Request(String name) {
        super(name);
    }

    public Request(String name, String searchName, String requestType){
        super(name);
        this.searchName = searchName;
        this.requestType = requestType;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}