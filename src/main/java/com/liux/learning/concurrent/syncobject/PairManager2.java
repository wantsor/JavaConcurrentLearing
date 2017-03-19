package com.liux.learning.concurrent.syncobject;

/**
 * Created by liuxian on 17/3/15.
 * 在对象上sync的测试
 */
public class PairManager2 extends PairManager {

    @Override
    public void increment() {
        //需要在同步块里面操作成员变量pair，所以外面使用一个temp变量来操作。
        Pair temp;
        synchronized (this) {
            pair.incrementX();
            Thread.yield();
            pair.incrementY();
            temp = getPair();
        }
        this.store(temp);
    }
}
