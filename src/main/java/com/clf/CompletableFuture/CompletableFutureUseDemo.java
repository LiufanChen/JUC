package com.clf.CompletableFuture;

import java.util.concurrent.*;

/**
 * ClassName: CompletableFutureUseDemo
 * Package: com.clf.CompletableFuture
 * Description:
 *
 * @Author clf
 * @Create 2025/6/5 13:31
 * @Version 1.0
 *
 *
 *
 * 异步任务结束时，会自动回调某个对象的方法
 * 主线程设置好回调后，不用关心异步任务的执行，异步任务之间可以顺序执行
 * 异步任务出错时，会自动回调某个对象的方法
 */
public class CompletableFutureUseDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "---come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (result > 5) { //模拟产生异常情况
                int i = 10 / 0;
            }
            System.out.println("----------1秒钟后出结果" + result);
            return result;
        }, executorService).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("计算完成 更新系统" + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println("异常情况：" + e.getCause() + " " + e.getMessage());
            return null;
        });
        System.out.println(Thread.currentThread().getName() + "先去完成其他任务");
        executorService.shutdown();
    }
}