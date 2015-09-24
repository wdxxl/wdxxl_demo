package com.wdxxl.jdk.generics;

public class MainAncDesc {

	public static void main(String[] args) {
		DescendantGen<Integer, String> dg = new DescendantGen<>(12,
				"The Value is:");

		System.out.print(dg.getObj2());
		System.out.println(dg.getObj1());
	}

}
