package com.wdxxl.powermock.basic02.news;

import java.io.File;

public class NewInMethod {
    public boolean callNewInMethod(String path) {
        File file = new File(path);
        System.out.println("I Say callNewInMethod!!");
        return file.exists();
    }
}
