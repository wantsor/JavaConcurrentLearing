package com.liux.learning.concurrent.syncobject;

/**
 * Created by liuxian on 17/3/15.
 * 检查Pair类运行情况
 */
public class PairChecker implements Runnable {

    public PairManager manager;

    public PairChecker(PairManager manager) {
        this.manager = manager;
    }

    public void run() {
        while (true) {
            manager.counter.incrementAndGet();
            manager.getPair().checkState();
        }
    }
}
