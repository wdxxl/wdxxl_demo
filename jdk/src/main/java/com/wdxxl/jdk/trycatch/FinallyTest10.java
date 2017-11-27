package com.wdxxl.jdk.trycatch;

// 1. finally语句在return语句执行之后return返回之前执行的。
public class FinallyTest10 {

    public static void main(String[] args) {
        System.out.println(test1());
    }

    @SuppressWarnings("finally")
    public static int test1() {
        int b = 20;
        try {
            System.out.println("try block");
            return b += 80;
        } catch (Exception e) {
            System.out.println("catch block");
        } finally {
            System.out.println("finally block");
            if (b > 25) {
                System.out.println("b > 25, b = " + b);
            }
            return b += 1;
        }
    }

}
/**
 * try block
 * finally block
 * b > 25, b = 100
 * 101
 */

// 说明return语句已经执行了再去执行finally语句，不过并没有直接返回，而是等finally语句执行完了再返回结果。
