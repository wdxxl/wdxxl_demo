package com.wdxxl.jdk.enums;

public class EnumDemo {
    public enum Priority {
        P0, P1, P2, P3, P4;
    }

    public static void main(String[] args) {
        try {
            System.out.println(Priority.valueOf("P13").toString());
            System.out.println(Priority.valueOf(null).toString());
            System.out.println(Priority.valueOf(" ").toString());
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("nonexisted");
        }
    }

}
