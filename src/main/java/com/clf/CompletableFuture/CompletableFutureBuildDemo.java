package com.clf.CompletableFuture;

/**
 * ClassName: CompletableFutureBuildDemo
 * Package: com.clf.CompletableFuture
 * Description:
 *
 * @Author clf
 * @Create 2025/6/5 13:22
 * @Version 1.0
 */

import java.util.concurrent.*;

/**
 * get()方法在Future计算完成之前会一直处在阻塞状态下，阻塞的方式和异步编程的设计理念相违背。
 * isDone()方法容易耗费cpu资源（cpu空转），
 * 对于真正的异步处理我们希望是可以通过传入回调函数，在Future结束时自动调用该回调函数，这样，我们就不用等待结果
 * CompletableFuture提供了一种观察者模式类似的机制，可以让任务执行完成后通知监听的一方。
 */
public class CompletableFutureBuildDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },executorService);
        System.out.println(completableFuture.get()); //null
        CompletableFuture<String> objectCompletableFuture = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello supplyAsync";
        },executorService);
        System.out.println(objectCompletableFuture.get());//hello supplyAsync
        executorService.shutdown();

    }
}
