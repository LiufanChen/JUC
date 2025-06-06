package com.clf.CAS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ClassName: AtomicStampedReferenceDemo
 * Package: com.clf.CAS
 * Description:
 *    ABA问题  版本号时间戳原子引用
 *   比如说一个线程1从内存位置V中取出A，这时候另一个线程2也从内存中取出A，并且线程2进行了一些操作将值变成了B，
 *   然后线程2又将V位置的数据变成A，这时候线程1进行CAS操作发现内存中仍然是A，预期ok，
 *   然后线程1操作成功--------尽管线程1的CAS操作成功，但是不代表这个过程就是没有问题的。
 * @Author clf
 * @Create 2025/6/6 15:32
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
class Book {
    private int id;
    private String bookName;
}

public class AtomicStampedReferenceDemo {
    public static void main(String[] args) {
        Book javaBook = new Book(1, "javaBook");
        AtomicStampedReference<Book> atomicStampedReference = new AtomicStampedReference<>(javaBook, 1);
        System.out.println(atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());

        Book mysqlBook = new Book(2, "mysqlBook");
        boolean b;
        b = atomicStampedReference.compareAndSet(javaBook, mysqlBook, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
        System.out.println(b + "\t" + atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());

        b = atomicStampedReference.compareAndSet(mysqlBook, javaBook, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
        System.out.println(b + "\t" + atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());
    }
}
/**
 * Book(id=1, bookName=javaBook)	1
 * true	Book(id=2, bookName=mysqlBook)	2
 * true	Book(id=1, bookName=javaBook)	3
 */