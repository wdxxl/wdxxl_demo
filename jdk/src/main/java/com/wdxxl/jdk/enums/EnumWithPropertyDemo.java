package com.wdxxl.jdk.enums;

public class EnumWithPropertyDemo {
    public enum Priority {
        P0("level0",100),
        P1("level1",200),
        P2("level2",300),
        P3("level3",400),
        P4("level4",500);

        private final String name;
        private final int value;

        private Priority(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

    }
    public static void main(String[] args) {
        System.out.println(
                Priority.P0.toString() + "," + Priority.P0.getName() + "," + Priority.P0.getValue());

        System.out.println("Priority List:");
        for (Priority p : Priority.values()) {
            System.out.println(p + " name= " + p.getName() + " ,value= " + p.getValue());
        }

        Priority ret;
        ret = Priority.valueOf("P0");
        System.out.println("Selected Priority: " + ret);
    }

}
