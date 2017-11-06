package com.wsfmn.habittracker;

import io.searchbox.annotations.JestId;

/**
 * Created by Fredric on 2017-11-05.
 */

public class ProfileName {
    private String name;

    @JestId
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProfileName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
