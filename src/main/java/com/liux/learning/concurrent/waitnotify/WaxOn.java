package com.liux.learning.concurrent.waitnotify;

import java.util.concurrent.TimeUnit;

/**
 * Created by liuxian on 17/3/17.
 * 打蜡的任务
 */
public class WaxOn implements Runnable {

    private Car car;

    public WaxOn(Car car) {
        this.car = car;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.print("Wax On!..");
                TimeUnit.MILLISECONDS.sleep(100);
                car.waxed();
                car.waitingForBuffing();
            }
        } catch (InterruptedException e) {
            System.out.println("exiting via interrupting");
        }
        System.out.println("\n Ending wax On task!");

    }
}
