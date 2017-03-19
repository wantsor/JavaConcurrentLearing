package com.liux.learning.concurrent.runninggame;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.System.out;

/**
 * 这是第一个比赛程序。
 * Created by liuxian on 17/3/12.
 */
public class OneTrack {

    private static final String[] PLAYER_NAMES = new String[]{"路飞","孙悟空","比鲁斯","三代目雷影","四代目火影","鸣人","孙悟饭"
            ,"黄猿","佐助", "维斯"};

    /**
     * 报名队列(非线程安全)
     * */
    private List<Player> signupPlayers = new LinkedList<Player>();

    /**
     * 初赛结果队列(有排序功能，且线程安全)
     * */
    private PriorityBlockingQueue<Player> preliminaries = new PriorityBlockingQueue<Player>();

    /*
    * 决赛结果队列(有排序功能，且线程安全)
    * */
    private PriorityBlockingQueue<Player> finals = new PriorityBlockingQueue<Player>();

    public void track() {
        /*
         * 赛跑分为以下几个阶段进行；
         *
         * 1、报名
         * 2、初赛，10名选手，分成两组，每组5名选手。
         * 分两次进行初赛（因为场地只有5条赛道，只有拿到进场许可的才能使用赛道，进行比赛）
         *
         * 3、决赛：初赛结果将被写入到一个队列中进行排序，只有成绩最好的前五名选手，可以参加决赛。
         *
         * 4、决赛结果的前三名将分别作为冠亚季军被公布出来
         * */

        //1. ============报名
        //因为需求是5条跑道，所以permits为5
        Semaphore runway = new Semaphore(5);
        this.signupPlayers.clear();
        for(int i=0; i < OneTrack.PLAYER_NAMES.length; ) {
            Player player = new Player(OneTrack.PLAYER_NAMES[i], ++i, runway);
            signupPlayers.add(player);
        }

        //2. ============初赛
        //这是裁判
        ExecutorService refereeService =  Executors.newFixedThreadPool(5);
        for (final Player player: this.signupPlayers) {
            Future<Result> future = refereeService.submit(player);
            new FutureThread(future, this.preliminaries, player).start();
        }
        //只有signupPlayers.length所有选手成绩都出来了，才能进入决赛
        //为什么是同步这个queue?
        synchronized (this.preliminaries) {
            while (this.preliminaries.size() < signupPlayers.size()) {
                try {
                    this.preliminaries.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace(out);
                }
            }
        }

        //3.============决赛(只有初赛结果的前5名可以参见)
        for(int index = 0; index < 5; index ++) {
            Player player = this.preliminaries.poll();
            Future<Result> finalFuture = refereeService.submit(player);
            new FutureThread(finalFuture, this.finals, player).start();
        }
        //! 只有当5位选手的成绩出来了，才能到下一步，公布成绩
        synchronized (this.finals) {
            while (this.finals.size() < 5) {
                try {
                    this.finals.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //4.============公布成绩
        for(int index = 0 ; index < 3 ; index++) {
            Player player = this.finals.poll();
            switch (index) {
                case 0:
                    System.out.println("第一名："  + player.getName() + "[" + player.getNumber() + "]，成绩：" + player.getResult().getTime() + "秒");
                    break;
                case 1:
                    System.out.println("第二名："  + player.getName() + "[" + player.getNumber() + "]，成绩：" + player.getResult().getTime() + "秒");
                    break;
                case 2:
                    System.out.println("第三名："  + player.getName() + "[" + player.getNumber() + "]，成绩：" + player.getResult().getTime() + "秒");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 这是计分线程，是为了保证产生比赛结果后，在计入PriorityBlockingQueue
     * 这样才有排列成绩的依据
     * @author yinwenjie
     *
     */
    private class FutureThread extends Thread {
        /**
         * 选手跑步任务(Player)的执行状态对象
         * */
        private Future<Result> future;

        /**
         * 跑步成绩出来以后，需要操作的队列
         * 把对应的选手加入队列，以便根据成绩排序
         * */
        private PriorityBlockingQueue<Player> achievementQueue;
        /**
         * 当前进行跑步的选手
         * */
        private Player player;

        public FutureThread(Future<Result> future, PriorityBlockingQueue<Player> achievementQueue, Player player) {
            this.future = future;
            this.achievementQueue = achievementQueue;
            this.player = player;
        }

        public void run() {
            if(this.future == null) {
                out.println("选手退赛，记分为0");
            } else {
                //当选手没跑完时会一直阻塞在这里
                try {
                    future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            //运行到这里就说明选手跑完了
            //无论什么结果都计入队列，通知主线程
            this.achievementQueue.put(player);
            synchronized (this.achievementQueue) {
                achievementQueue.notify();
            }
        }
    }

    public static void main(String args[]) {
        new OneTrack().track();
    }
}
