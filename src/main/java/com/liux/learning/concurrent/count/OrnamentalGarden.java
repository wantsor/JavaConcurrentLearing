package com.liux.learning.concurrent.count;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuxian on 17/3/15.
 * 目的：验证并发情况下，通过sync同步方法，保证多个线程同时向一个变量写入时数据的正确。
 */
public class OrnamentalGarden {
    public static void main(String args[]) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for(int i=0; i < 5; i++ ) {
            executor.execute(new Entrance(i));
        }
        //休眠3秒然后结束线程池
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Entrance.cancel();
        executor.shutdown();
        try {
            //等待250ms后强制终止，并且可以返回终止时是否有线程未结束
            if(!executor.awaitTermination(250, TimeUnit.MILLISECONDS)) {
                System.out.print(" Some tasks were not terminal");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Total : " + Entrance.getTotalCount());
        System.out.println("Sum numbers: " + Entrance.sumEntrances());

//        int i = Integer.MAX_VALUE;
//        System.out.print("i=" + i + " i+1=" + ++i);
    }
}
