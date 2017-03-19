package com.liux.learning.concurrent.syncobject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuxian on 17/3/15.
 * 主测试类
 * question: 为什么要运行10多秒程序才会停下来？
 */
public class CriticalSection {

    static void testApproaches(PairManager manager1, PairManager manager2) {

        PairManipulator pm1 = new PairManipulator(manager1);
        PairManipulator pm2 = new PairManipulator(manager2);

        PairChecker pc1 = new PairChecker(manager1);
        PairChecker pc2 = new PairChecker(manager2);

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(pm1);
        executor.execute(pm2);
        executor.execute(pc1);
        executor.execute(pc2);
        executor.shutdown();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("pm1:" + pm1 + "\npm2:" + pm2);
        System.exit(0);
    }

    public static void main(String args[]) {
        System.out.println("begin test!");
        PairManager manager1 = new PairManager1();
        PairManager manager2 = new PairManager2();
        testApproaches(manager1,manager2);
    }
}
