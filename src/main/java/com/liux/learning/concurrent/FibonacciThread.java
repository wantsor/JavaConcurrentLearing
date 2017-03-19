package com.liux.learning.concurrent;

/**
 * Created by liuxian on 17/3/12.
 */
public class FibonacciThread implements Runnable {

    private int n;
    private  static int taskCount = 0;
    private  final int id = taskCount++;

    public FibonacciThread(int n) {
        this.n = n;
    }

    public void run() {
        getFibonacciArray();
    }

    public int fibonacci(int i) {
        if(i <= 1) return i;
        else {
            return fibonacci(i - 1) + fibonacci(i - 2);
        }
    }

    public void getFibonacciArray() {
        String fibonacciArrayStr = "[";
        for(int i = 0; i< n; i++) {
            fibonacciArrayStr += fibonacci(i) + ",";
        }
        fibonacciArrayStr += "]";
        System.out.println("#" + id + "(" + "fibonacci "+ n + " is: " + fibonacciArrayStr + ");");
    }
}
