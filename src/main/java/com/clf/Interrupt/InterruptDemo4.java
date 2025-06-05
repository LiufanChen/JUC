package com.clf.Interrupt;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: InterruptDemo4
 * Package: com.clf.LockSupport
 * Description:
 *     如果线程处于正常活动状态，
 *     那么会将该线程的中断标志设置为true，
 *     仅此而已，被设置中断标志的线程将继续正常运行，
 *     不受影响，所以interrupt()并不能真正的中断线程，
 *     需要被调用的线程自己进行配合才行，对于不活动的线程没有任何影响。
 *
 *   阻塞  内核态
 *  sleep   不释放锁 暂停线程时间		 TIMED_WAITING
 *  wait    释放锁   等待通知或条件    W / TIMED_
 *  join   不释放锁  等待另一个线程   W / TIMED_
 *
 * @Author clf
 * @Create 2025/6/5 20:36
 * @Version 1.0
 */

public class InterruptDemo4 {
    public static void main(String[] args) {
        //实例方法interrupt()仅仅是设置线程的中断状态位为true，不会停止线程
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 300; i++) {
                System.out.println("------: " + i);
            }
            /**
             * ------: 298
             * ------: 299
             * ------: 300
             * t1线程调用interrupt()后的中断标志位02：true
             */
            System.out.println("t1线程调用interrupt()后的中断标志位02：" + Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        System.out.println("t1线程默认的中断标志位：" + t1.isInterrupted());//false

        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();//true
        /**
         * ------: 251
         * ------: 252
         * ------: 253
         * t1线程调用interrupt()后的中断标志位01：true
         */
        System.out.println("t1线程调用interrupt()后的中断标志位01：" + t1.isInterrupted());//true

        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //2000毫秒后，t1线程已经不活动了，不会产生任何影响
        System.out.println("t1线程调用interrupt()后的中断标志位03：" + t1.isInterrupted());//false

    }
}