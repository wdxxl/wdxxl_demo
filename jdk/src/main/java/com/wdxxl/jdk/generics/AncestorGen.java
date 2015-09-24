package com.wdxxl.jdk.generics;

public class AncestorGen<T> {
	private T Obj1;

	public AncestorGen(T obj) {
		this.setObj1(obj);
	}

	public T getObj1() {
		return Obj1;
	}

	public void setObj1(T obj) {
		Obj1 = obj;
	}
}
