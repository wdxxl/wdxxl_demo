package com.wdxxl.commons.lang3.time;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class TestDateUtils {
    public static void main(String[] args) {
        Date olderDate = new Date();
        String olderDateStr = olderDate.toString();

        Date newDate = new Date(olderDateStr);
        System.out.println(newDate.getTime());

        // 15 days early than now.
        System.out.println(DateUtils.addDays(new Date(), -15).getTime());// 1444266586629

        String hah = Long.toString(System.currentTimeMillis());

        Date hahd = new Date(Long.valueOf(hah));
        System.out.println(hahd.getTime());
    }
}
