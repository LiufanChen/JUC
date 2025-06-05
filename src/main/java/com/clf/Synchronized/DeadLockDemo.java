package com.clf.Synchronized;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: DeadLockDemo
 * Package: com.clf.Synchronized
 * Description: 死锁

 *  jconsole
 *
 * @Author clf
 * @Create 2025/6/5 19:28
 * @Version 1.0
 */
public class DeadLockDemo {
    static  Object a=new Object();
    static  Object b=new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (a){
                System.out.println("t1线程持有a锁，试图获取b锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b){
                    System.out.println("t1线程获取到b锁");
                }
            }
        },"t1").start();

        new Thread(() -> {
            synchronized (b){
                System.out.println("t2线程持有a锁，试图获取a锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a){
                    System.out.println("t2线程获取到a锁");
                }
            }
        },"t2").start();
    }
}


/**
 *
 *  *  如何排查死锁
 *  *   jps -l
 *  *   jstack 进程编号
 *  *Java stack information for the threads listed above:
 *  * ===================================================
 *  * "t1":
 *  *         at com.clf.Synchronized.DeadLockDemo.lambda$main$0(DeadLockDemo.java:35)
 *  *         - waiting to lock <0x00000006226e3200> (a java.lang.Object)
 *  *         - locked <0x00000006226e31f0> (a java.lang.Object)
 *  *         at com.clf.Synchronized.DeadLockDemo$$Lambda$14/0x0000000800c01200.run(Unknown Source)
 *  *         at java.lang.Thread.run(java.base@17.0.2/Thread.java:833)
 *  * "t2":
 *  *         at com.clf.Synchronized.DeadLockDemo.lambda$main$1(DeadLockDemo.java:49)
 *  *         - waiting to lock <0x00000006226e31f0> (a java.lang.Object)
 *  *         - locked <0x00000006226e3200> (a java.lang.Object)
 *  *         at com.clf.Synchronized.DeadLockDemo$$Lambda$15/0x0000000800c01418.run(Unknown Source)
 *  *         at java.lang.Thread.run(java.base@17.0.2/Thread.java:833)
 *  *
 *  * Found 1 deadlock.
 *
 */