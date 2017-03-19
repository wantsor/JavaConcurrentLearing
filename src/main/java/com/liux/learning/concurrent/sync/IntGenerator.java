package com.liux.learning.concurrent.sync;

/**
 * Created by liuxian on 17/3/13.
 */
public abstract class IntGenerator {

    private Boolean canceled = false;

    public abstract int next();

    public void cancel() {
        canceled = true;
    }

    public Boolean isCancel() {
        return canceled;
    }
}
