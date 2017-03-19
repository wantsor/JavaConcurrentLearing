package com.liux.learning.concurrent.interrupt;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liuxian on 17/3/16.
 */
public class IOBlocked implements Runnable{

    InputStream in;
    public IOBlocked(InputStream is) {
        this.in = is;
    }

    public void run() {
        System.out.print("io block interrupte");
        try {
            in.read();
        } catch (IOException e) {
            //这里不会进入，因为IO不会被中断？
            if(Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupted from blocked I/O");
            } else {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Exiting IOBlocked.run()");
    }
}
