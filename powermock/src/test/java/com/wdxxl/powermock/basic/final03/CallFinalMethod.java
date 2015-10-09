package com.wdxxl.powermock.basic.final03;

public class CallFinalMethod {
    public boolean callFinalMethod(FinalMethod finalMethod) {
        System.out.println("I Say going toCall FinalMethod!!");
        return finalMethod.isAlive();
    }
}
