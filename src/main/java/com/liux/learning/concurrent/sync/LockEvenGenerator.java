package com.liux.learning.concurrent.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liuxian on 17/3/14.
 */

public class LockEvenGenerator extends IntGenerator{
    private int initNumber = 0;
    private Lock lock = new ReentrantLock();
    @Override
    public int next() {
        try {
            lock.lock();
            ++ initNumber;
            Thread.yield();
            ++ initNumber;
            return initNumber;
        }
        finally {
            lock.unlock();
        }
    }

    public static void main(String args[]) {
        EvenChecker.test(new LockEvenGenerator(), 10);
    }
}
