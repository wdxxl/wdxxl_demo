package com.wdxxl.powermock.basic10.mothods;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class WithLotsMethods {

    public String methodOfString(String input) {
        System.out.println("You should not see this message.");
        return input;
    }

    public String methodOfDataObjects(Integer i, Long l, Short s, Boolean flag, Float f, Double d) {
        String result = "You should not see this message.";
        return result;
    }

    public String methodOfByte(Byte b) {
        String result = "You should not see this message.";
        return result;
    }

    public String methodOfChar(char b) {
        String result = "You should not see this message.";
        return result;
    }

    public String methodOfClass(MyClass myClass) {
        String result = "You should not see this message.";
        return result;
    }

    public String methodOfListClass(List<MyClass> myClass) {
        String result = "You should not see this message.";
        return result;
    }

    public String methodOfSetClass(Set<MyClass> myClass) {
        String result = "You should not see this message.";
        return result;
    }

    public String methodOfMapClass(Map<MyClass, MyClass> myClass) {
        String result = "You should not see this message.";
        return result;
    }
}
