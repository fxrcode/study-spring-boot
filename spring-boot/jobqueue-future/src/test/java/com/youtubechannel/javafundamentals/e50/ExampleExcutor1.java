package com.youtubechannel.javafundamentals.e50;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

import static java.lang.System.Logger.Level.INFO;

/**
 * Java Fundamentals - Lesson 50 - Executor Services
 * Laurentiu Spilca
 */
public class ExampleExcutor1 {
    System.Logger LOGGER = System.getLogger("Example1");

    @Test
    public void exec1() {
        ExecutorService service = null;
        try {
            int n = Runtime.getRuntime().availableProcessors();
            service = Executors.newFixedThreadPool(n);
            int i = 42;
            Runnable r = () -> {
                try {
                    LOGGER.log(INFO, ":) " +
                            Thread.currentThread().getName()); // name
                    LOGGER.log(INFO, "Sleeping for 3 sec");
                    Thread.sleep(3 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Future<?> f = service.submit(r); // throwing the ball into the pool
            LOGGER.log(INFO, ":( " + Thread.currentThread().getName());
        } finally {
            if (service != null) {
                service.shutdown();
                LOGGER.log(INFO, "Service shutdown completed");
            }
        }
    }

    @Test
    public void exec2() {
        ExecutorService service = null;
        try {
            int n = Runtime.getRuntime().availableProcessors();
            service = Executors.newFixedThreadPool(n);
            int i = 42;
            Callable<TaskResult> r = () -> {
                LOGGER.log(INFO, ":) " +
                        Thread.currentThread().getName()); // name
                LOGGER.log(INFO, "Sleeping for 3 sec");
                Thread.sleep(3 * 1000);
                return new TaskResult(i);
            };
            Future<?> f = service.submit(r); // throwing the ball into the pool
            LOGGER.log(INFO, ":( " + Thread.currentThread().getName());
            LOGGER.log(INFO, f.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (service != null) {
                service.shutdown();
                LOGGER.log(INFO, "Service shutdown completed");
            }
        }
    }

    @Test
    public void exec3() {
        ExecutorService service = null;
        try {
            int n = Runtime.getRuntime().availableProcessors();
            service = Executors.newFixedThreadPool(n);
            List<Integer> seeds = Arrays.asList(1,2,3,4,5);
            List<Future<Integer>> futures= Lists.newArrayList();
            for (int s : seeds) {
                futures.add(service.submit(new FooTask(s, LOGGER)));
            }
            for (Future<Integer> f : futures) {
                LOGGER.log(INFO, f.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
    }
}

class FooTask implements Callable<Integer> {
    final int number;
    final System.Logger LOGGER;

    FooTask(int num, System.Logger logger) {
        this.number = num;
        LOGGER = logger;
    }

    @Override
    public String toString() {
        return "FooTask{" +
                "number='" + number + '\'' +
                '}';
    }

    @Override
    public Integer call() throws Exception {
        int fact = 1;
        int sleeptime = new Random().nextInt(10) * 1000;
        LOGGER.log(INFO, "index: " + number + "\t" + Thread.currentThread().getName() + ", sleep for " + sleeptime);
        // ...
        Thread.sleep(sleeptime);
        for(int count = number; count > 1; count--) {
            fact = fact * count;
        }
        return fact;
    }
}

class TaskResult {
    UUID id;
    int count;

    public TaskResult(int count) {
        this.id = UUID.randomUUID();
        this.count = count;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "id=" + id +
                ", count=" + count +
                '}';
    }
}