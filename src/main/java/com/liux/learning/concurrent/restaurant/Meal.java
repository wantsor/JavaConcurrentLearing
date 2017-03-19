package com.liux.learning.concurrent.restaurant;

/**
 * Created by liuxian on 17/3/19.
 * 生产的产品
 */
public class Meal {
    private final int orderNum;

    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal " + orderNum;
    }

}
