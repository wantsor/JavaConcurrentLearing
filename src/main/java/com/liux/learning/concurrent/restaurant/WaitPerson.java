package com.liux.learning.concurrent.restaurant;

/**
 * Created by liuxian on 17/3/19.
 * 服务者，一次只能端一份肉，必须等待主厨做好才能端走，然后通知主厨做下一份肉。
 */
public class WaitPerson implements Runnable{
    private Restaurant restaurant;

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    if (restaurant.meal == null) {
                        wait();
                    }
                }
                System.out.println("Waitperson got " + restaurant.meal);
                //服务员消费了meal,通知主厨生产者可以生产下一份meal了
                synchronized (restaurant.chef) {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("WaitPerson interrupt");
        }
    }
}
