package com.liux.learning.concurrent.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * Created by liuxian on 17/3/16.
 * 验证通过sleep进入block状态被打断的情况
 */
public class SleepBlocked implements Runnable{
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("interrupte from sleep");
        }
        System.out.println("Exiting SleepBlocked.run()");
    }

}
