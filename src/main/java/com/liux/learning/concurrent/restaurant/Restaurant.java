package com.liux.learning.concurrent.restaurant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liuxian on 17/3/19.
 * 餐厅，主测试类，测试目的
 * 1.验证消费者／生产者的任务交替衔接.
 */
public class Restaurant {
    Meal meal;
    public Chef chef = new Chef(this);
    public WaitPerson waitPerson = new  WaitPerson(this);
    ExecutorService exec = Executors.newCachedThreadPool();

    public Restaurant() {
        exec.execute(chef);
        exec.execute(waitPerson);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}
