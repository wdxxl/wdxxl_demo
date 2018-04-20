package com.wdxxl.jdk.map;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 模拟本地常见缓存，例如2000条 DB查询记录，放在本地作为查询缓存queue，移除老数据，
 * queue数据如果访问一次，就刷新本地改数据在queue的位置
 *
 * @author wdxxl
 *
 */
public class LinkedHashMapDemo {

    public static void main(String[] args) {
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("a", "a-value");
        linkedHashMap.put("b", "b-value");
        linkedHashMap.put("c", "c-value");
        linkedHashMap.put("d", "d-value");

        // 顺序输出 Key
        for (String key : linkedHashMap.keySet()) {
            System.out.println("key = " + key);
        }

        // 顺序输出 Value
        for (Entry<?, ?> entry : linkedHashMap.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }

        // 移除最老的数据
        removeFirstElement(linkedHashMap);

        // 顺序输出 Key
        for (String key : linkedHashMap.keySet()) {
            System.out.println("key = " + key);
        }

        // 刷新老数据在queue的位置，放在最后 "b"
        String bValue = linkedHashMap.get("b");
        linkedHashMap.remove("b");
        linkedHashMap.put("b", bValue);

        // 顺序输出 Key
        for (String key : linkedHashMap.keySet()) {
            System.out.println("key = " + key);
        }
    }

    private static void removeFirstElement(Map<String, String> linkedHashMap) {
        if (linkedHashMap.isEmpty())
            return;
        linkedHashMap.remove(linkedHashMap.keySet().iterator().next());
    }

}
