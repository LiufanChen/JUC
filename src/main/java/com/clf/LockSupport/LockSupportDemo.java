package com.clf.LockSupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * ClassName: LockSupportDemo
 * Package: com.clf.LockSupport
 * Description:
 *  LockSupport类使用了一种名为Permit（许可）的概念来做到阻塞和唤醒线程的功能，每个线程都有一个许可（Permit），许可证只能有一个，累加上限是1。
 *  阻塞: Peimit许可证默认没有不能放行，所以一开始调用park()方法当前线程会阻塞，直到别的线程给当前线程发放peimit，park方法才会被唤醒
 *   唤醒: 调用unpack(thread)方法后 就会将thread线程的许可证peimit发放，会自动唤醒park线程，即之前阻塞中的LockSupport.park()方法会立即返回。
 * @Author clf
 * @Create 2025/6/5 22:09
 * @Version 1.0
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        /**
         * t1	 -----------come in
         * t2	 ----------发出通知
         * t1	 ----------被唤醒
         */
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t -----------come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t ----------被唤醒");
        }, "t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "\t ----------发出通知");
        }, "t2").start();

    }
}
