package com.wdxxl.powermock.basic04.statics;

public class CallStaticMethod {
    public boolean callStaticMethod() {
        System.out.println("I Say going toCall Static!!");
        return StaticMethod.isExist();
    }
}
