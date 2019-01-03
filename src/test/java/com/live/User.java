package com.live;

import java.io.Serializable;

public class User implements Serializable {
    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public User(int id, String name, int old) {
        this.id = id;
        this.name = name;
        this.old = old;
    }

    public int getOld() {
        return old;
    }

    public void setOld(int old) {
        this.old = old;
    }

    public void setName(String name) {
        this.name = name;

    }

    private int id;
    private String name;
    private int old;
}
