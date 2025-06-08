package com.clf.ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * ClassName: StampedLockDemo
 * Package: com.clf.ReentrantLock
 * Description:
 *   锁饥饿问题：ReentrantReadWriteLock实现了读写分离，但是一旦读操作比较多的时候，想要获取写锁就变得比较困难了，因此当前有可能会一直存在读锁，而无法获得写锁。
 *  StampedLock类的乐观读锁方式--->采取乐观获取锁，其他线程尝试获取写锁时不会被阻塞，在获取乐观读锁后，还需要对结果进行校验
 * StampedLock是不可重入的，危险（如果一个线程已经持有了写锁，在去获取写锁的话会造成死锁）
 * ● 所有获取锁的方法，都返回一个邮戳，stamp为零表示失败，其余都表示成功
 * ● 所有释放锁的方法，都需要一个邮戳，这个stamp必须是和成功获取锁时得到的stamp一致
 *● StampedLock有三种访问模式：
 *   ○ Reading（读模式悲观）：功能和ReentrantReadWriteLock的读锁类似
 *   ○ Writing（写模式）：功能和ReentrantReadWriteLock的写锁类似
 *   ○ Optimistic reading（乐观读模式）：无锁机制，类似与数据库中的乐观锁，支持读写并发，很乐观认为读时没人修改，假如被修改在实现升级为悲观读模式
 *
 * @Author clf
 * @Create 2025/6/8 18:31
 * @Version 1.0
 */
public class StampedLockDemo {
    static int number = 37;
    static StampedLock stampedLock = new StampedLock();

    public void write() {
        long stamp = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName() + "\t" + "写线程准备修改");
        try {
            number = number + 13;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "写线程结束修改");
    }

    public void read() {
        long stamp = stampedLock.tryOptimisticRead();

        int result = number;

        System.out.println("4秒前 stampedLock.validate方法值（true 无修改 false有修改）" + "\t" + stampedLock.validate(stamp));
        for (int i = 0; i < 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + " 正在读取...." + i + "秒后" + "stampedLock.validate方法值（true 无修改 false有修改）" + "\t" + stampedLock.validate(stamp));
        }
        if (!stampedLock.validate(stamp)) {
            System.out.println("有人修改----------有写操作");
            stamp = stampedLock.readLock();
            try {
                System.out.println("从乐观读升级为悲观读");
                result = number;
                System.out.println("重新悲观读后result：" + result);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "finally value: " + result);

    }


    public static void main(String[] args) {
        StampedLockDemo resource = new StampedLockDemo();
        new Thread(() -> {
            resource.read();
        }, "readThread").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + " come in");
            resource.write();
        }, "writeThread").start();
    }
}
/**
 * 4秒前 stampedLock.validate方法值（true 无修改 false有修改）	true
 * readThread	 正在读取....0秒后stampedLock.validate方法值（true 无修改 false有修改）	true
 * readThread	 正在读取....1秒后stampedLock.validate方法值（true 无修改 false有修改）	true
 * writeThread	 come in
 * writeThread	写线程准备修改
 * writeThread	写线程结束修改
 * readThread	 正在读取....2秒后stampedLock.validate方法值（true 无修改 false有修改）	false
 * readThread	 正在读取....3秒后stampedLock.validate方法值（true 无修改 false有修改）	false
 * 有人修改----------有写操作
 * 从乐观读升级为悲观读
 * 重新悲观读后result：50
 * readThread	finally value: 50
 */