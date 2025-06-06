package com.clf.Atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: AtomicIntegerDemo
 * Package: com.clf.Atomic
 * Description:
 *
 * @Author clf
 * @Create 2025/6/6 15:40
 * @Version 1.0
 */
class MyNumber {
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addPlusPlus() {
        atomicInteger.getAndIncrement();
    }

}

public class AtomicIntegerDemo {

    public static final int SIZE = 50;

    public static void main(String[] args) throws InterruptedException {
        MyNumber myNumber = new MyNumber();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        for (int i = 1; i <= SIZE; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 10; j++) {
                        myNumber.addPlusPlus();
                    }
                } finally {
                    countDownLatch.countDown();
                }
                System.out.println("当前线程"+Thread.currentThread().getName() + "\t" + myNumber.atomicInteger.get());
            }, String.valueOf(i)).start();

        }
        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + "\t" + "result: " + myNumber.atomicInteger.get());//main	result: 500
    }
}