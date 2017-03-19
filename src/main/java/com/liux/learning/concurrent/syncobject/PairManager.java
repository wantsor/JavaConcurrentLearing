package com.liux.learning.concurrent.syncobject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liuxian on 17/3/15.
 * 执行increament的管理抽象类
 */
public abstract class PairManager {

    public AtomicInteger counter = new AtomicInteger(0);
    Pair pair = new Pair();

    private List<Pair> list = Collections.synchronizedList(new ArrayList<Pair>());

    /*用于验证同步的抽象方法*/
    public abstract void increment();

    public void store(Pair p) {
       list.add(p);
       //这里为什么需要sleep一下？
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*为什么每次要返回新的?*/
    public synchronized Pair getPair() {
        return new Pair(pair.getX(), pair.getY());
    }
}
