package com.wdxxl.jdk.generics;

public class DescendantGen<T, U> extends AncestorGen<T> {
	private U obj2;

	public DescendantGen(T obj1, U obj2) {
		super(obj1);
		this.setObj2(obj2);
	}

	public U getObj2() {
		return obj2;
	}

	public void setObj2(U obj2) {
		this.obj2 = obj2;
	}
}
