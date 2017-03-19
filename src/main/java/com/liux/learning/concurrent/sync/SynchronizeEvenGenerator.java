package com.liux.learning.concurrent.sync;

/**
 * Created by liuxian on 17/3/14.
 */
public class SynchronizeEvenGenerator extends IntGenerator {
    private int intNumber = 0;
    synchronized public int next() {
        ++ intNumber;
        Thread.yield();
        ++ intNumber;
        return intNumber;
    }

    public static void main(String args[]) {
        EvenChecker.test(new SynchronizeEvenGenerator(), 10);
    }
}
