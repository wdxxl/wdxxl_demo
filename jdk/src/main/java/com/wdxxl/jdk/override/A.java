package com.wdxxl.jdk.override;

public class A {
	public static String s = "执行父类的静态方法";

	// 在父类中定义一个静态方法
	public static void prints() {
		System.out.println(s);
	}

	public void show() {
		System.out.println("show" + s);
	}
}
