package com.clf.Atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * ClassName: AccumulatorCompareDemo
 * Package: com.clf.Atomic
 * Description:
 *
 * @Author clf
 * @Create 2025/6/6 15:51
 * @Version 1.0
 */
class ClickNumber {
    int number = 0;

    public synchronized void clickBySynchronized() {
        number++;
    }

    AtomicLong atomicLong = new AtomicLong(0);

    public void clickByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    LongAdder longAdder = new LongAdder();

    public void clickByLongAdder() {
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);

    public void clickByLongAccumulator() {
        longAccumulator.accumulate(1);
    }
}

public class AccumulatorCompareDemo {
    public static final int _1W = 10000;
    public static final int THREAD_NUMBER = 50;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        long StartTime;
        long endTime;
        CountDownLatch countDownLatch1 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch2 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch3 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch4 = new CountDownLatch(THREAD_NUMBER);

        StartTime = System.currentTimeMillis();
        for (int i = 1; i <= 50; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickBySynchronized();
                    }
                } finally {
                    countDownLatch1.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        System.out.println("------costTime: " + (endTime - StartTime) + " 毫秒" + "\t clickBySynchronized: " + clickNumber.number);

        StartTime = System.currentTimeMillis();
        for (int i = 1; i <= 50; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickByAtomicLong();
                    }
                } finally {
                    countDownLatch2.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch2.await();
        endTime = System.currentTimeMillis();
        System.out.println("------costTime: " + (endTime - StartTime) + " 毫秒" + "\t clickByAtomicLong: " + clickNumber.atomicLong.get());

        StartTime = System.currentTimeMillis();
        for (int i = 1; i <= 50; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickByLongAdder();
                    }
                } finally {
                    countDownLatch3.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch3.await();
        endTime = System.currentTimeMillis();
        System.out.println("------costTime: " + (endTime - StartTime) + " 毫秒" + "\t clickByLongAdder: " + clickNumber.longAdder.sum());

        StartTime = System.currentTimeMillis();
        for (int i = 1; i <= 50; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickByLongAccumulator();
                    }
                } finally {
                    countDownLatch4.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch4.await();
        endTime = System.currentTimeMillis();
        System.out.println("------costTime: " + (endTime - StartTime) + " 毫秒" + "\t clickByLongAccumulator: " + clickNumber.longAccumulator.get());

    }
}
/**
 * ------costTime: 1313 毫秒	 clickBySynchronized: 50000000
 * ------costTime: 825 毫秒	 clickByAtomicLong: 50000000
 * ------costTime: 92 毫秒	 clickByLongAdder: 50000000
 * ------costTime: 61 毫秒	 clickByLongAccumulator: 50000000
 */