package com.liux.learning.concurrent.waitnotify;

/**
 * Created by liuxian on 17/3/19.
 */
public class Task implements Runnable {
    static Blocker blocker = new Blocker();


    public void run() {
        blocker.waitingCall();
    }
}
