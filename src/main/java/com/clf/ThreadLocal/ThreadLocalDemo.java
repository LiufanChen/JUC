package com.clf.ThreadLocal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: ThreadLocalDemo
 * Package: com.clf.ThreadLocal
 * Description:
 * 实现每一个线程都有自己专属的本地变量副本（自己用自己的变量不用麻烦别人，不和其他人共享，人人有份，人各一份）。主
 * 要解决了让每个线程绑定自己的值，通过使用get()和set()方法，获取默认值或将其改为当前线程所存的副本的值从而避免了线程安全问题。
 *
 * @Author clf
 * @Create 2025/6/6 16:03
 * @Version 1.0
 */
class House {
    int saleCount = 0;

    public synchronized void saleHouse() {
        saleCount++;
    }

    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);

    public void saleVolumeByThreadLocal() {
        saleVolume.set(1 + saleVolume.get());
    }


}

public class ThreadLocalDemo {
    public static void main(String[] args) {
        House house = new House();
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                int size = new Random().nextInt(5) + 1;
                try {
                    for (int j = 1; j <= size; j++) {
                        house.saleHouse();
                        house.saleVolumeByThreadLocal();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t" + "号销售卖出：" + house.saleVolume.get());
                } finally {
                    house.saleVolume.remove();
                }
            }, String.valueOf(i)).start();

        }
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "共计卖出多少套： " + house.saleCount);
    }
}
/**
 * 3	号销售卖出：1
 * 4	号销售卖出：3
 * 5	号销售卖出：4
 * 2	号销售卖出：3
 * 1	号销售卖出：5
 * main	共计卖出多少套： 16
 */