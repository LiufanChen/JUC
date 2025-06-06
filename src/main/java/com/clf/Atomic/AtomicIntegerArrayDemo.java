package com.clf.Atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * ClassName: AtomicIntegerArrayDemo
 * Package: com.clf.Atomic
 * Description:
 *
 * ● AtomicIntegerArray：整型数组原子类
 * ● AtomicLongrArray：长整型数组原子类
 * ● AtomicReferenceArray：用类型数组原子类
 *
 * @Author clf
 * @Create 2025/6/6 15:43
 * @Version 1.0
 */
public class AtomicIntegerArrayDemo {
    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1, 2, 3, 4, 5});
//        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]);
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println(atomicIntegerArray.get(i));
        }
        System.out.println();
        int tempInt = 0;
        tempInt = atomicIntegerArray.getAndSet(0, 1122);
        System.out.println(tempInt + "\t" + atomicIntegerArray.get(0));
        tempInt = atomicIntegerArray.getAndIncrement(0);
        System.out.println(tempInt + "\t" + atomicIntegerArray.get(0));
    }
}