package com.wdxxl.lucene.counts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MockDataUtil {
    public static final Integer LENGTH = 6;
    public static final String[] IDS = {"1", "2", "3", "4", "5", "6"};
    public static final String[] EMAILS = {
        "aa@wdxxl.com",
        "bb@wdxxl.com",
        "cc@wdxxl.com",
        "dd@sina.org",
        "ee@sinobot.org",
        "ff@sina.org"};
    public static final String[] CONTENTS = {
        "welcome to visit the space, hope you will like it",
        "hello like boy",
        "my name is cc I like nothing",
        "I like football",
        "I like football and I like basketball",
        "I like movie and swim"};
    public static final String[] NAMES = {"zhangsan", "lisi", "john", "jetty", "mike", "jake"};
    public static final int[] ATTACHS = {2, 3, 1, 4, 5, 6};
    public static final Date[] DATES;
    public static Map<String, Float> SCORES = new HashMap<>();

    static {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DATES = new Date[6];
        try {
            DATES[0] = sdf.parse("2013-05-12");
            DATES[1] = sdf.parse("2014-05-12");
            DATES[2] = sdf.parse("2015-05-12");
            DATES[3] = sdf.parse("2016-05-12");
            DATES[4] = sdf.parse("2017-05-12");
            DATES[5] = sdf.parse("2013-05-12");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // For Boost
        SCORES.put("wdxxl.com", 2.0f);
        SCORES.put("sinobot.org", 1.5f);
    }
}
