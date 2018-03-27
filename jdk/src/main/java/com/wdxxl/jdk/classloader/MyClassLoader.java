package com.wdxxl.jdk.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

// https://www.jianshu.com/p/3036b46f1188
// http://sharewind.iteye.com/blog/1582869
public class MyClassLoader extends ClassLoader {

    public Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = null;
        File classFile = new File(name + ".class");
        if (classFile.exists()) {
            try (FileChannel fileChannel = new FileInputStream(classFile).getChannel();) {
                MappedByteBuffer mappedByteBuffer = fileChannel.map(MapMode.READ_ONLY, 0, fileChannel.size());
                byte[] result = new byte[(int) fileChannel.size()];
                if (mappedByteBuffer.remaining() > 0) {
                    mappedByteBuffer.get(result, 0, mappedByteBuffer.remaining());
                }
                clazz = defineClass("Hello", result, 0, result.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (clazz == null) {
            throw new ClassNotFoundException(name);
        }
        return clazz;
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> clazz = myClassLoader.findClass("/Users/wdxxl/Hello");
        Method sayHello = clazz.getMethod("sayHello");
        sayHello.invoke(null, null);
    }
}
