package lt.bit.java2.demo;

import sun.jvm.hotspot.runtime.Threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class ThreadsDemo {

    public static void main(String[] args) {
        ThreadsDemo threadsDemo = new ThreadsDemo();

        threadsDemo.testCounterInThreads();

        threadsDemo.testExecutors();
    }

    void testCounterInThreads() {

        List<Long> list = Collections.synchronizedList(new ArrayList<>());

        Counter c = new Counter();

        ExecutorService executor = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        executor.execute(() -> {
            for (int j = 0; j < 1000001; j++) {
                c.inc();
                list.add(c.getCounter());
            }
        });
        executor.execute(() -> {
            for (int j = 0; j < 1000000; j++) {
                c.dec();
                list.add(c.getCounter());
            }
        });


        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Counter = " + c.getCounter());
        System.out.println("List size = " + list.size());

    }

    

    private void runningTime(Runnable runnable) {
        long start = System.nanoTime();
        runnable.run();
        long stop = System.nanoTime();
        System.out.println("Running time " + (stop - start) / 1e6 + "ms");
    }

    private double longCalc(double i) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Math.sin(i);
    }

    void testExecutors() {

        runningTime(() -> {
            double result = 0.0;
            for (long i = 0; i < 1000; i++) {
                result += longCalc(i);
            }
            System.out.println("result = " + result);
        });

        runningTime(() -> {
            ExecutorService executor = Executors.newCachedThreadPool();

            List<Future<Double>> list = new ArrayList<>();

            IntStream.range(0, 1000).forEach(i -> {
                Future<Double> future = executor.submit(() -> longCalc(i));
                list.add(future);
            });

            double result = list.stream().mapToDouble(x -> {
                try {
                    return x.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                throw new ArithmeticException("Kazkas negerai");
            }).sum();

            executor.shutdown();

            System.out.println("result = " + result);
        });
    }
}


class Counter {

    long counter;

    public synchronized void inc() {
        counter++;
    }

    public synchronized void dec() {
        counter--;
    }

    public long getCounter() {
        return counter;
    }
}