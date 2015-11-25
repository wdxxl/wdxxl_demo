package com.wdxxl.restful.user;

public class User {
    private long id;
    private String name;

    public User() {}

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + "]";
    }

}
