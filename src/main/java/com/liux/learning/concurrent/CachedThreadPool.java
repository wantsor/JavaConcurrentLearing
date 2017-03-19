package com.liux.learning.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liuxian on 17/3/12.
 */
public class CachedThreadPool {

    public static void main(String args[]) {
        ExecutorService executor = Executors.newScheduledThreadPool(2);
        //ExecutorService executor = Executors.newCachedThreadPool();
        for(int i=0; i<5; i++ ) {
            executor.execute(new FibonacciThread(10));
        }
        executor.shutdown();
    }
}
