package com.wdxxl.jdk.collections;

public class Student {
    @Override
    public String toString() {
        return "Student [id=" + id + ", stuNo=" + stuNo + ", name=" + name + "]";
    }

    private final int id;
    private final String stuNo;
    private final String name;

    public Student(int id, String stuNo, String name) {
        this.id = id;
        this.stuNo = stuNo;
        this.name = name;
    }

    public int getId() {
        return id;
    }

}
