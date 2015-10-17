package com.wdxxl.powermock.basic03.finals;

public class FinalMethod {
    public final boolean isAlive() {
        System.out.println("You should not see this message.");
        return false;
    }
}
