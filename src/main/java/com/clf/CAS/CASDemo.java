package com.clf.CAS;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: CASDemo
 * Package: com.clf.CAS
 * Description:
 * ● 如果相匹配，那么处理器会自动将该位置更新为新值
 * ● 如果不匹配，处理器不做任何操作，多个线程同时执行CAS操作只有一个会成功。
 *
 * @Author clf
 * @Create 2025/6/6 15:10
 * @Version 1.0
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2022) + "\t" + atomicInteger.get());//true	2022
        System.out.println(atomicInteger.compareAndSet(5, 2022) + "\t" + atomicInteger.get());//false	2022
    }
}

/**
 * Unsafe类是CAS的核心类，由于Java方法无法直接访问底层系统，需要通过本地（native）方法来访问，
 * Unsafe相当于一个后门，基于该类可以直接操作特定内存的数据。Unsafe类存在于sun.misc包中，其内
 * 部方法操作可以像C的指针一样直接操作内存，因此Java中CAS操作的执行依赖于Unsafe类的方法。
 * CAS是一种系统原语，原语属于操作系统用语范畴，是由若干条指令组成的，用于完成某个功能的一个过程，
 * 并且原语的执行必须是连续的，在执行过程中不允许被中断，也就是说CAS是一条CPU的原子指令，不会造
 * 成所谓的数据不一致问题。
 */