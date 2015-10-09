package com.wdxxl.powermock.basic.static04;

public class StaticMethod {
    public static boolean isExist() {
        System.out.println("You should not see this message.");
        return false;
    }
}
