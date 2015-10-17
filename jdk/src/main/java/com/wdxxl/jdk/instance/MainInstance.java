package com.wdxxl.jdk.instance;

public class MainInstance {
	public static void main(String[]args){
		Parent parent = new Parent();
        Son son = new Son();
        System.out.println(Parent.class.isInstance(parent));
        System.out.println(Son.class.isInstance(parent));
		System.out.println(Parent.class.isInstance(son));
		System.out.println(Son.class.isInstance(son));

        System.out.println("=========================");
        isParent(parent);
        isParent(son);
	}

    private static boolean isParent(Parent parent) {
        if (Parent.class.isInstance(parent) && Son.class.isInstance(parent)) {
            System.out.println("is Son");
            return false;
        } else {
            System.out.println("is Praent");
            return true;
        }
    }
}
