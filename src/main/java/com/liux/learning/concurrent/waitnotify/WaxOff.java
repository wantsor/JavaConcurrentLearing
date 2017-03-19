package com.liux.learning.concurrent.waitnotify;

import java.util.concurrent.TimeUnit;

/**
 * Created by liuxian on 17/3/17.
 * 抛光任务
 * 开始一直无法shutdown，是因为把InterruptedException的try/catch写在了while里面，导致还在无限循环中，而interruptException以后状态
 * 被再次置为false，所以下次用Thread.interrupted还是false，无法跳出循环
 */
public class WaxOff implements Runnable {

    private Car car;

    public WaxOff(Car car) {
        this.car = car;
    }

    public void run() {
        //静态方法是判断当前线程
        try {
            while (!Thread.interrupted()) {
                car.waitingForWaxing();
                System.out.print("wax off !!");
                TimeUnit.MILLISECONDS.sleep(100);
                car.bufferd();
            }
        } catch (InterruptedException e) {
            System.out.println("exiting via interrupting");
        }
        System.out.println("\n Ending Wax Off task");
    }
}
