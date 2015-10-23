package com.wdxxl.jdk.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lists {
    public static void main(String[] args) {
        List emptyList = Collections.<String>emptyList();
        System.out.println(emptyList.contains("l1"));

        List<String> testList = new ArrayList();
        testList.add("l1");
        System.out.println(testList.contains("l1"));
    }
}
