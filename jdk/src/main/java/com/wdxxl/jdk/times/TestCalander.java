package com.wdxxl.jdk.times;

import java.util.Calendar;
import java.util.Date;

public class TestCalander {

    public static void main(String[] args) {
        Date current = new Date();

        Calendar middleCal = Calendar.getInstance();
        middleCal.add(Calendar.HOUR, -3);

        Calendar yesterDayCal = Calendar.getInstance();
        yesterDayCal.add(Calendar.DAY_OF_WEEK, -1);

        System.out.println(yesterDayCal.getTime().before(middleCal.getTime()));
        System.out.println(middleCal.getTime().before(current));

        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, -34);
        System.out.println(now.getTime());
    }

}
