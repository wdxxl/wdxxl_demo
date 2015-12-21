package com.wdxxl.jesque.job;

import java.util.Date;
import java.util.List;

public class TestAction implements Runnable {
    final int tInt;
    final double tDoulbe;
    final boolean tBoolean;
    final String tString;
    final List<Object> tList;
    private Date runTime;

    public TestAction(int tInt, double tDoulbe, boolean tBoolean, String tString,
            List<Object> tList) {
        this.tDoulbe = tDoulbe;
        this.tInt = tInt;
        this.tBoolean = tBoolean;
        this.tString = tString;
        this.tList = tList;
    }

    @Override
    public void run() {
        System.out.println("TestAction [tInt=" + tInt + ", tDoulbe=" + tDoulbe + ", tBoolean="
                + tBoolean + ", tString=" + tString + ", tList=" + tList + ", runTime=" + runTime
                + "]");
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

}
