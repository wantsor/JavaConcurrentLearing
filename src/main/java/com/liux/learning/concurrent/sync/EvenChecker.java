package com.liux.learning.concurrent.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liuxian on 17/3/13.
 */
public class EvenChecker implements Runnable {

    private IntGenerator generator;

    private final Integer id;

    public EvenChecker(IntGenerator g, int id) {
        this.generator = g;
        this.id = id;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + "start to check even!");
        while(!generator.isCancel()) {
            int nextNumber = generator.next();
            if(nextNumber % 2 != 0) {
                generator.cancel();
                System.out.println(nextNumber + "is not even!");
            }
        }
    }

    public static void test(IntGenerator g, int count) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for(int i=0; i < count; i++) {
            executor.execute(new EvenChecker(g, i));
        }
        executor.shutdown();
    }
}
