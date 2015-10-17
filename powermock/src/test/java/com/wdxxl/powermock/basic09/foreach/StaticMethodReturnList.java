package com.wdxxl.powermock.basic09.foreach;

import java.util.Arrays;
import java.util.List;

public class StaticMethodReturnList {
    public static List<String> getList() {
        return Arrays.asList("never see 1", "never see 2", "never see 3");
    }
}
