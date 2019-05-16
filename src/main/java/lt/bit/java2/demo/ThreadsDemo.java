package lt.bit.java2.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ThreadsDemo {

    public static void main(String[] args) {
        ThreadsDemo threadsDemo = new ThreadsDemo();

        threadsDemo.testCounterInThreads();

        threadsDemo.testExecutors();
    }

    /*
     * Testuojam kaip viskas griuna jei keli thread'ai
     */
    void testCounterInThreads() {

        // Jei ne Collections.synchronizedList - tai viskas sugriutu
        List<Long> list = Collections.synchronizedList(new ArrayList<>());

        Counter c = new Counter();

        ExecutorService executor = Executors.newFixedThreadPool(2);

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

    /*
     * Testuojam kaip skaiciuoja ilgai trunkancius dalykus.
     * Pradzioje bandome nuosekliai, o po to su ExecutorService.
     */
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

// jei ne synchronized - tai gerai neveiktu
class Counter {

    private long counter;

    synchronized void inc() {
        counter++;
    }

    synchronized void dec() {
        counter--;
    }

    long getCounter() {
        return counter;
    }
}