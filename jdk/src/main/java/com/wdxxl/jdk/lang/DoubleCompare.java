package com.wdxxl.jdk.lang;

public class DoubleCompare {

    public static void main(String[] args) {

        Double d1 = new Double(234.01);
        Double d2 = new Double(234.01);
        System.out.println(d1 == d2);
        System.out.println(d1.equals(d2));

        double d3 = 234.01d;
        double d4 = 234.01d;
        System.out.println(d3 == d4);

        double d5 = 0;
        double d6 = 0.0;
        System.out.println(d5 == d6);
    }

}
