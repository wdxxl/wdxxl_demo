package com.wdxxl.powermock.basic.private05;

public class CallPrivateMethod {
    public boolean callPrivateMethod() {
        System.out.println("I Say going toCall PrivateMethod!!");
        return isExist();
    }

    private boolean isExist() {
        System.out.println("You should not see this message.");
        return false;
    }
}
