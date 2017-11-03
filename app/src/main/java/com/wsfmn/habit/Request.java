package com.wsfmn.habit;

/**
 * Created by Fredric on 2017-10-21.
 */

public class Request {
    private String name;

    public Request(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
