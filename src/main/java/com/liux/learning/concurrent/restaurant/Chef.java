package com.liux.learning.concurrent.restaurant;

import java.util.concurrent.TimeUnit;

/**
 * Created by liuxian on 17/3/19.
 * 主厨，生产者
 * 默认没有订单时，等待，当接到服务者的通知后开始生产肉。
 * 一次只能生产一份肉，做好以后通知服务者领取
 */
public class Chef implements Runnable {
    private Restaurant restaurant;
    private int count = 0; //订单数量， 坐满10个今天任务就完成了

    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.meal != null) {
                        wait();
                    }
                }
                if (++count == 10) {
                    System.out.println("卖完啦， 明天请早!");
                    //exit
                    restaurant.exec.shutdownNow();
                }
                System.out.println("订单来啦！！");
                synchronized (restaurant.waitPerson) {
                    Meal meal = new Meal(count);
                    restaurant.meal = meal;
                    restaurant.waitPerson.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Chef interrupted");
        }
    }
}
