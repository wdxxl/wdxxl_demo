package com.wdxxl.jdk.collections;

import java.util.HashMap;
import java.util.Map;

public class Maps {
    public static void main(String[] args) {
        Map<String, String> mapa = new HashMap<>();
        mapa.put("aa", "aa");

        System.out.println(mapa.get("aa") == null ? "null" : "not null");
        System.out.println(mapa.get("bb") == null ? "null" : "not null");
    }
}
