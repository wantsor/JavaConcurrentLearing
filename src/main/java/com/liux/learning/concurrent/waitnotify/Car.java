package com.liux.learning.concurrent.waitnotify;

/**
 * Created by liuxian on 17/3/17.
 * 模拟了汽车打蜡，抛光，再打蜡的过程，必须等待打蜡完成后才能抛光
 * 通过wait来阻塞方法A，并让出锁给其他的同步方法， 然后其他同步方法完成后再用notifyAll通知方法A继续。
 * car有一个waxOn状态，表示是否正在打蜡
 */
public class Car {

    //true表示已经打蜡完成，false是还未打蜡
    private boolean waxOn = false;

    //完成打蜡，通知可以做其他事了
    public synchronized void waxed() {
        waxOn = true;
        notifyAll();
    }

    //完成抛光，又可以打蜡了
    public synchronized void bufferd() {
        waxOn = false;
        notifyAll();
    }

    //等待打蜡，在还没开始打蜡前，先等待
    public synchronized void waitingForWaxing() throws InterruptedException {
        while (waxOn == false) {
            wait();
        }
    }

    //等待抛光，在打蜡没结束前，先等待
    public synchronized void waitingForBuffing() throws  InterruptedException {
        while (waxOn == true) {
            wait();
        }
    }
}
