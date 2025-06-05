package com.clf.Interrupt;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: InterruptDemo3
 * Package: com.clf.LockSupport
 * Description:   通过Thread类自带的中断API实例方法实现----在需要中断的线程中不断监听中断状态，一旦发生中断，就执行相应的中断处理业务逻辑stop线程。
 *   interrupt() 和isInterrupted()组合
 * @Author clf
 * @Create 2025/6/5 20:32
 * @Version 1.0
 */
public class InterruptDemo3 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " isInterrupted()的值被改为true，t1程序停止");
                    break;
                }
                System.out.println("-----------hello isInterrupted()");
            }
        }, "t1");
        t1.start();

        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //t2向t1放出协商，将t1中的中断标识位设为true，希望t1停下来
        new Thread(() -> t1.interrupt(), "t2").start();

        //当然，也可以t1自行设置
        t1.interrupt();

    }
}
/**
 * -----------hello isInterrupted()
 * -----------hello isInterrupted()
 * -----------hello isInterrupted()
 * -----------hello isInterrupted()
 * t1 isInterrupted()的值被改为true，t1程序停止
 */