package com.liux.learning.concurrent.waitnotify;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuxian on 17/3/19.
 * 验证目的：
 * 1.notify只会唤醒当前线程的wait
 * 2.notifyAll只会唤醒等待当前这个锁的任务，在这里就是Task的所有线程，而Task2的线程就没有被唤醒，也就是同一个对象的锁才被唤醒了。
 */
public class NotifyVsNotifyAll {

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {
            exec.execute(new Task());
        }
        exec.execute(new Task2());
        Timer timer = new Timer();
        //验证notify只唤醒了自己关联的一个线程?
        timer.schedule(new TimerTask() {
            boolean prod = true;

            @Override
            public void run() {
                if (prod) {
                    System.out.println("\n notify!");
                    Task.blocker.prod();
                    prod = false;
                } else {
                    System.out.println("\n notify all");
                    Task.blocker.prodAll();
                    prod = true;
                }
            }
        }, 4, 4);

        TimeUnit.SECONDS.sleep(5);
        timer.cancel();
        System.out.println("Timer canceled");
        TimeUnit.MILLISECONDS.sleep(500);

        System.out.println("Task2.blocker.prodAll()");
        Task2.blocker.prodAll();
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("Shutting down");
        exec.shutdownNow();
    }
}
