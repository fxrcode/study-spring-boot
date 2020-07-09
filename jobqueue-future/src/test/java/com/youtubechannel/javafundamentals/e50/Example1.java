package com.youtubechannel.javafundamentals.e50;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Java Fundamentals - Lesson 50 - Executor Services
 *  Laurentiu Spilca
 */
public class Example1 {
    @Test
    public void exec2() {
        ExecutorService service = null;
        try {
            int n = Runtime.getRuntime().availableProcessors();
            service = Executors.newFixedThreadPool(n);
            Runnable r = () -> System.out.println(":) " +
                    Thread.currentThread().getName()); // name
            Future<?> f = service.submit(r); // throwing the ball into the pool
            System.out.println(":( " + Thread.currentThread().getName());
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
    }
}
