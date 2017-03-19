package com.liux.learning.concurrent.count;

/**
 * Created by liuxian on 17/3/15.
 * 计数器类，这个需要线程安全的计数
 */
public class Count {
    private int count = 0;

    //可以去掉sync看看效果，就同步不了了
    public synchronized int increment() {
        int temp = ++count;
        return temp;
    }

    public synchronized int value() {
        return count;
    }
}
