package com.wdxxl.jdk.override;

public class B extends A {
	public static String s="执行子类的静态方法";

    public static void prints(){
        System.out.println("子类"+s);
    }
    
    public void show(){
        System.out.println("子类show"+s);
    }
}
