package com.wdxxl.jdk.ref;

import java.util.Random;

/**
 * 构建一个客户端提交订单给服务器, 服务器接收客户端的订单提交通常是高并发的业务场景
 * @author wdxxl
 */
public class ThreadScoreData {
    // 引入线程本地变量来封装有状态的 价格信息
    private static ThreadLocal<Integer> buyThreadPrice = new ThreadLocal<Integer>();

    public static void main(String[] args) {
        // 多线程环境
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //synchronized (ThreadScoreData.class) { // 可以，但是性能不高
                        // 执行业务方法
                        int price = new Random().nextInt(10000);
                        System.out.println("产生线程名称:" + Thread.currentThread().getName() + ",价格为:" + price);
                        buyThreadPrice.set(price);
                        // 进入A处理模块
                        new A().getPriceInfo();
                        // 进入B处理模块
                        new B().getPriceInfo();
                    //}
                }
            }).start();
        }
    }

    // 服务器当中的A模块， 检查我们的余额
    static class A {
        public void getPriceInfo() {
            System.out.println(Thread.currentThread().getName() + "进入A处理模块, 处理价格信息:" + buyThreadPrice.get());
        }
    }

    // 服务器当中的B模块， 检查我们的余额
    static class B {
        public void getPriceInfo() {
            System.out.println(Thread.currentThread().getName() + "进入B处理模块, 处理价格信息:" + buyThreadPrice.get());
        }
    }
}
