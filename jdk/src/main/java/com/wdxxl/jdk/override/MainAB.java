package com.wdxxl.jdk.override;

public class MainAB {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		A a = new A();
		a.prints();// 执行父类的静态方法
		a.show();// show执行父类的静态方法
		B b = new B();
		b.prints();// 子类执行子类的静态方法
		b.show();// 子类show执行子类的静态方法
		A c = new B();
		c.prints();// 执行父类的静态方法 (Static method cannot be override)
		c.show();// 子类show执行子类的静态方法
	}

}
