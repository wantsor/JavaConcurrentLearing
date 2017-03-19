package com.liux.learning.concurrent.syncobject;

/**
 * Created by liuxian on 17/3/15.
 * 数据操作类,runnable ,运行PairManager的increament测试
 */
public class PairManipulator implements Runnable{

    private PairManager manager;

    public PairManipulator(PairManager manager) {
        this.manager = manager;
    }

    public void run() {
        while (true) {
            manager.increment();
        }
    }

    public String toString() {
        return "Pair: " + manager.getPair() + " counter = " + manager.counter.get();
    }
}
