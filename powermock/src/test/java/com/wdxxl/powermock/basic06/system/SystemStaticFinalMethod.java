package com.wdxxl.powermock.basic06.system;

public class SystemStaticFinalMethod {
    public boolean callSystemFinalMethod(String str) {
        System.out.println("I Say going to call String.isEmpty!!");
        return str.isEmpty();
    }

    public String callSystemStaticMethod(String str) {
        System.out.println("I Say going to call System.getProperty!!");
        return System.getProperty(str);
    }
}
