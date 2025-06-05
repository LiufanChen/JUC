package com.clf.Interrupt;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: InterruptDemo
 * Package: com.clf.LockSupport
 * Description:   通过一个volatile变量实现
 *
 * @Author clf
 * @Create 2025/6/5 19:37
 * @Version 1.0
 */
public class InterruptDemo {
    static volatile boolean isStop = false; //volatile表示的变量具有可见性

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    System.out.println(Thread.currentThread().getName() + " isStop的值被改为true，t1程序停止");
                    break;
                }
                System.out.println("-----------hello volatile");
            }
        }, "t1").start();
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            isStop = true;
        }, "t2").start();

    }
}
/**
 * -----------hello volatile
 * -----------hello volatile
 * -----------hello volatile
 * -----------hello volatile
 * -----------hello volatile
 * -----------hello volatile
 * t1 isStop的值被改为true，t1程序停止
 */
