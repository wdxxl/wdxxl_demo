package com.wdxxl.jdk.trycatch;

// 在try块中有System.exit(0);这样的语句，System.exit(0);是终止Java虚拟机JVM的，连JVM都停止了，所有都结束了，当然finally语句也不会被执行到。
public class FinallyTest60 {

    public static void main(String[] args) {
        System.out.println(test6());
    }

    @SuppressWarnings("finally")
    public static int test6() {
        int b = 20;
        try {
            System.out.println("try block");
            System.exit(0);
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
