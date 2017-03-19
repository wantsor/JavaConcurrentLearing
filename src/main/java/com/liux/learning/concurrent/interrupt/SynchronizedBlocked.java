package com.liux.learning.concurrent.interrupt;

/**
 * Created by liuxian on 17/3/16.
 * 验证通过同步方法进入block状态
 */
public class SynchronizedBlocked implements Runnable {

    public SynchronizedBlocked() {
        new Thread() {
            public void run() {
                f();  //通过另外一个线程进入同步方法，并且就一直锁住自己
            }
        }.start();
    }

    //拿到锁以后就不会释放了
    public synchronized void f() {
        while (true) {
            Thread.yield();
        }
    }

    public void run() {
        System.out.println("try to call f()");
        //将会一直阻塞，因为另外一个线程已经拿到锁
        f();
    }
}
