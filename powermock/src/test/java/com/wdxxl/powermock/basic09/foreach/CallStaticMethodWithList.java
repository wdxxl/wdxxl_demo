package com.wdxxl.powermock.basic09.foreach;

import java.util.List;

public class CallStaticMethodWithList {

    public void runForEach() {
        List<String> lists = StaticMethodReturnList.getList();
        if (lists != null && lists.size() > 0) {
            for (String str : lists) {
                System.out.println(str);
            }
        }
    }

}
