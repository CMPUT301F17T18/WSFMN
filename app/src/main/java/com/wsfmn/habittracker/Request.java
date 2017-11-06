package com.wsfmn.habittracker;

import io.searchbox.annotations.JestId;
/**
 * Created by Fredric on 2017-10-21.
 */

public class Request extends ProfileName{
    private String searchName;
    public Request(String name) {
        super(name);
    }

    public Request(String name, String searchName){
        super(name);
        this.searchName = searchName;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
}