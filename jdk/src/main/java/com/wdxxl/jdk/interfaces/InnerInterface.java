package com.wdxxl.jdk.interfaces;

public class InnerInterface {
    private ProductBuilder productBuilder;

    static interface ProductProcessor {
        public void processProduct(ProductBuilder productBuilder);
    }

    private ProductProcessor processor;

    public int importProductXML(ProductProcessor processor) {
        this.processor = processor;
        processor.processProduct(productBuilder);
        return 0;
    }

    public static void main(String[] args) {
        InnerInterface innerInterface = new InnerInterface();
        innerInterface.productBuilder = new ProductBuilder();
        innerInterface.importProductXML(new Driver());
        innerInterface.processor.processProduct(innerInterface.productBuilder);
    }
}
