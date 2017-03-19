package com.liux.learning.concurrent.waitnotify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuxian on 17/3/17.
 * 测试主类 验证目的：
 * 1.验证同一个对象上不同的同步方法，可以通过wait()阻塞，而把锁释放出来让给另外一个同步方法去执行。
 * 2.通过notifyAll来唤醒之前的wait
 */
public class WaxOMatic {

    public static void main(String[] args) throws Exception {
        Car car = new Car();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new WaxOff(car));
        executor.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5); // wait for while
        executor.shutdownNow(); //interrupt all tasks
    }


}
