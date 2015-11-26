package com.wdxxl.jsoup;

public class StringUtils {
    public static String repalceAllSeparate(String source) {
        return source.replaceAll("\r", "").replaceAll("\n", "");
    }
}
