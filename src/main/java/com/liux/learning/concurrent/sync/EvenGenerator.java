package com.liux.learning.concurrent.sync;

/**
 * Created by liuxian on 17/3/13.
 */
public class EvenGenerator extends IntGenerator {

    int initNumber = 0;

    public int next() {
        ++ initNumber;
        //Thread.yield();  //more chance to error
        ++ initNumber;
        return initNumber;
    }

    public static void main(String args[]) {
        EvenChecker.test(new EvenGenerator(), 2);
    }
}
