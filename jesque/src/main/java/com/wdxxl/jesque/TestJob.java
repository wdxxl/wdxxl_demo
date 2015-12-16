package com.wdxxl.jesque;

import java.util.Date;

public class TestJob implements Runnable {
    final String arg1;
    final String arg2;

    public TestJob(String arg1, String arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public void run() {
        System.out.println(
                "Job ran at=" + new Date() + ", with arg1 = " + arg1 + ", and arg2= " + arg2);
    }
}
