package com.clf.CompletableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * ClassName: CompletableFutureDemo
 * Package: com.clf.CompletableFuture
 * Description:
 *
 * @Author clf
 * @Create 2025/6/5 12:50
 * @Version 1.0
 */

// Runnable接口+Callable接口+Future接口和FutureTask实现类。
// 多线程、有返回、异步任务
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask(new MyThread());
        Thread t1 = new Thread(futureTask); //开启一个异步线程
        t1.start();
        t1.sleep(2000); // 2s 后返回
        System.out.println(futureTask.get()); //有返回hello Callable
    }
}


class MyThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("--------come in");
        return "hello Callable";
    }
}