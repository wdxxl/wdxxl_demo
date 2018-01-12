package com.wdxxl.jdk.ref;

import java.lang.ref.WeakReference;

public class WeakRefDemo {

    public static void main(String[] args) {
        WeakRefDemo weakRefDemo = new WeakRefDemo();
        Demo demo = weakRefDemo.new Demo("Hello WeakRef");
        WeakReference<Demo> wr = new WeakReference<Demo>(demo);
        System.out.println(wr.get().toString());
        demo = null;
        System.out.println(wr.get().toString());
        System.gc();
        System.out.println(wr.get());
    }

    class Demo {
        private String value;

        public Demo(String value) {
            this.value = value;
        }

        public String toString() {
            return this.value;
        }
    }
}
// Hello WeakRef
// Hello WeakRef
// null
