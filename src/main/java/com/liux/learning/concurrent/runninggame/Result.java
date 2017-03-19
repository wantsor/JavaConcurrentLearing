package com.liux.learning.concurrent.runninggame;

/**
 * Created by liuxian on 17/3/12.
 */
public class Result {
    /*
    * 本次比赛的时间
    * */
    private float time;

    public Result(float time) {
        this.time = time;
    }

    public float getTime() {
        return this.time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
