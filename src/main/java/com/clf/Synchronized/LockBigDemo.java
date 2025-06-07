package com.clf.Synchronized;

/**
 * ClassName: LockBigDemo
 * Package: com.clf.Synchronized
 * Description:
 * * 锁粗化
 *  * 假如方法中首尾相接，前后相邻的都是同一个锁对象，那JIT编译器会把这几个synchronized块合并为一个大块
 *  * 加粗加大范围，一次申请锁使用即可，避免次次的申请和释放锁，提高了性能
 * @Author clf
 * @Create 2025/6/7 22:56
 * @Version 1.0
 */
public class LockBigDemo {
    static Object objectLock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (objectLock) {
                System.out.println("111111111111");
            }
            synchronized (objectLock) {
                System.out.println("222222222222");
            }
            synchronized (objectLock) {
                System.out.println("333333333333");
            }
            synchronized (objectLock) {
                System.out.println("444444444444");
            }
            //底层JIT的锁粗化优化
            synchronized (objectLock) {
                System.out.println("111111111111");
                System.out.println("222222222222");
                System.out.println("333333333333");
                System.out.println("444444444444");
            }
        }, "t1").start();
    }
}