package com.liux.learning.concurrent.syncobject;

/**
 * Created by liuxian on 17/3/15.
 * 在方法上sync的测试
 */
public class PairManager1 extends PairManager {

    @Override
    public synchronized void increment() {
        pair.incrementX();
        Thread.yield();
        pair.incrementY();
        store(this.getPair());
    }
}
