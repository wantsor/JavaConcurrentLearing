package com.liux.learning.concurrent.syncobject;

/**
 * Created by liuxian on 17/3/15.
 * 验证increament线程安全
 */
public class Pair {

    private int x,y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pair() {
        this(0,0);
    }

    public void incrementX() {
        x++;
    }
    public void incrementY() {
        y++;
    }

    public int getX() { return x;}
    public int getY() { return y;}

    public class PairValueNotEqualException extends RuntimeException {
        public PairValueNotEqualException() {
            super("pair value is not equal!");
        }
    }

    public void checkState() {
        if (x != y ) {
            throw new PairValueNotEqualException();
        }
    }

    public String toString() {
        return "x=" + x + " and y=" + y;
    }
}
