package com.wdxxl.jdk.interfaces;

public class Driver implements InnerInterface.ProductProcessor {

    @Override
    public void processProduct(ProductBuilder productBuilder) {
        System.out.println("do something with Driver...");
        productBuilder.executeProductBuilder();
    }

}
