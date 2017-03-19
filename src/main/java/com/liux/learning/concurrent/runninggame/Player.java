package com.liux.learning.concurrent.runninggame;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

/**
 * Created by liuxian on 17/3/12.
 */
public class Player implements Callable<Result>, Comparable<Player> {
    /*选手编号*/
    private int number;
    /*选手名称*/
    private String name;
    /*最低速度*/
    private float minSpeed;
    /*本次比赛结果*/
    private Result result;
    /*跑道*/
    private Semaphore runway;

    public Player(String name, int number, Semaphore runway) {
        this.name = name;
        this.number = number;
        this.runway = runway;
        //最低速度设置
        this.minSpeed = 8f;
    }

    private Result doRun() throws Exception {
        //生成即时速度
        float presentSpeed = 0f;
        presentSpeed = this.minSpeed * (1.0f + new Random().nextFloat());
        if(presentSpeed > 14f) {
            presentSpeed = 14f;
        }

        //计算跑步速度
        BigDecimal caculation = new BigDecimal(100).divide(new BigDecimal(presentSpeed), 3, RoundingMode.HALF_UP);
        float presentTime = caculation.floatValue();

        //让线程等待presentSpeed的时间，模拟跑步的过程
        synchronized (this) {
            this.wait((long) (presentTime * 1000f));
        }

        //返回跑步结果
        this.result = new Result(presentTime);
        return result;
    }

    public int compareTo(Player o) {
        /*
        * 两个选手间比较时间，小的就更快
        * */
        Result myResult = this.getResult();
        Result targetResult = o.getResult();

        //如果自己或者对手的result为null,说明比晒结果出了问题
        //只有退赛的情况会没有结果？？
        if(myResult == null) {
            return 1;
        }

        if(targetResult == null) {
            return -1;
        }

        // 耗时越少的，应该在"成绩"队列排在越前面
        if(myResult.getTime() < targetResult.getTime()) {
            return -1;
        } else {
            return 1;
        }
    }

    public Result call() throws Exception {
        try {
            //申请上跑道,再申请到以前都会阻塞
            this.runway.acquire();
            return this.doRun();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            // 都要进入初赛结果排序，如果进了exception成绩为0
            this.runway.release();
        }

        //如果执行到这里则说明发生了异常,时间直接赋最大值
        this.result = new Result(Float.MAX_VALUE);
        return result;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Result getResult() {
        return result;
    }
}
