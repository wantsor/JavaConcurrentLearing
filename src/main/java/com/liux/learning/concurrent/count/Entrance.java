package com.liux.learning.concurrent.count;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuxian on 17/3/15.
 * 大门类，在这里存储所有的门 ，每个门都有自己的id,和自己的通过门的计数
 */
public class Entrance implements Runnable {
    //计数器类
    private static Count count = new Count();
    //门的集合类
    private static List<Entrance> entrances = new ArrayList() ;

    private int number = 0;

    private final int id;

    private static volatile Boolean canceled = false;

    public Entrance(int id) {
        this.id = id;
        //把自己存到list中防止线程结束被垃圾回收？
        entrances.add(this);
    }

    //获取自己这扇门的数量
    public synchronized int getValue() {
        return this.number;
    }

    //这里是直接通过count类获取总数
    public static int getTotalCount() {
       return count.value();
    }
    //这是通过加所有的门的数量来获取总数，可以跟count做比较，验证并发的问题
    public static int sumEntrances(){
        int sum = 0;
        for(Entrance entrance : entrances) {
            sum += entrance.getValue();
        }
        return sum;
    }

    public static void cancel() {
        canceled = true;
    }

    public String toString() {
        return "Entrance " + id + ": " + getValue();
    }

    public void run() {
        while (!canceled) {
            synchronized (this) {
                ++number;
            }
            int temp = count.increment();
            System.out.println(this + " Total: " + temp);

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("sleep interrupted");
            }
        }
        System.out.println("Stopping " + this);
    }
}
