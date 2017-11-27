package com.wdxxl.jdk.trycatch;

public class FinallyTest11 {

    public static void main(String[] args) {
        System.out.println(test1());
    }

    public static String test1() {
        try {
            System.out.println("try block");
            return test2();
        } finally {
            System.out.println("finally block");
        }
    }

    public static String test2() {
        System.out.println("return statment");
        return "after return";
    }

}
/**
 * try block
 * return statment
 * finally block
 * after return
 */

// try中的return语句先执行了但并没有立即返回，等到finally执行结束后再