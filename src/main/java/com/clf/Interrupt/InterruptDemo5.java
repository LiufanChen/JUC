package com.clf.Interrupt;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: InterruptDemo5
 * Package: com.clf.LockSupport
 * Description:
 *   如果线程处于阻塞状态（例如sleep,wait,join状态等），
 *   在别的线程中调用当前线程对象的interrupt方法，
 *   那么线程将立即退出被阻塞状态（interrupt状态也将被清除），并抛出一个InterruptedException异常。
 * @Author clf
 * @Create 2025/6/5 21:52
 * @Version 1.0
 */
public class InterruptDemo5 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " 中断标志位为：" + Thread.currentThread().isInterrupted() + " 程序停止");
                    break;
                }
                //sleep方法抛出InterruptedException后，中断标识也被清空置为false，如果没有在
                //catch方法中调用interrupt方法再次将中断标识置为true，这将导致无限循环了
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    //Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                System.out.println("-------------hello InterruptDemo3");

            }
        }, "t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            t1.interrupt();
        }, "t2").start();
    }


}
