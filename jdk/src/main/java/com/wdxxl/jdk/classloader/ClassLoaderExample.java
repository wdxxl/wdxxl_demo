package com.wdxxl.jdk.classloader;

import java.util.HashSet;

public class ClassLoaderExample {

    public static void main(String[] args) {
        System.out.println(HashSet.class.getClassLoader()); // Bootstrap ClassLoader
        System.out.println(Employee.class.getClassLoader()); // Application ClassLoader
        System.out.println(sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader());// Extension ClassLoader
        System.out.println(sun.net.spi.nameservice.dns.DNSNameServiceDescriptor.class.getClassLoader());
    }

}
