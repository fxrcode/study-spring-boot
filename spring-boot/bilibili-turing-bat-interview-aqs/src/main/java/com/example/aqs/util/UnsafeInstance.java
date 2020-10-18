package com.example.aqs.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author: Turing, Yang Guo
 * @version: V1.0
 * @description: in order to bypass JVM, and directly manipulate memory. Have to use Unsafe.
 */
public class UnsafeInstance {
    public static Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        int j=1;
        reflectGetUnsafe().loadFence();
        int i= 0;
    }
}
