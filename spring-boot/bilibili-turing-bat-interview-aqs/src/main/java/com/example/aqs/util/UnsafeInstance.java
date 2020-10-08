package com.example.aqs.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeInstance {
    public static Unsafe reflectGetUnsafe() {
        try {
            // have to use bootstrapClassloader to get Unsafe, rather new Unsafe()
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
