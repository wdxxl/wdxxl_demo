package com.wdxxl.powermock.basic.static04;

public class CallStaticMethod {
    public boolean callStaticMethod() {
        System.out.println("I Say going toCall Static!!");
        return StaticMethod.isExist();
    }
}
