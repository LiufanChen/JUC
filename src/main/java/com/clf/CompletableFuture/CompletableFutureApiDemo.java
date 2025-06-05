package com.clf.CompletableFuture;

import java.util.concurrent.*;

/**
 * ClassName: CompletableFutureApiDemo
 * Package: com.clf.CompletableFuture
 * Description:
 *  thenApply --->计算结果存在依赖关系，这两个线程串行化---->由于存在依赖关系（当前步错，不走下一步），当前步骤有异常的话就叫停
 *  handle --->计算结果存在依赖关系，这两个线程串行化---->有异常也可以往下走一步
 *
 * @Author clf
 * @Create 2025/6/5 18:17
 * @Version 1.0
 */
public class CompletableFutureApiDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }, threadPool).thenApply(f -> {
            System.out.println("222");
            return f + 2;
        }).handle((f, e) -> {
            System.out.println("3333");
            int i=10/0;
            return f + 2;

//             thenApply(f -> {
//            System.out.println("3333");
//            return f + 2;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("----计算结果" + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println(e.getCause());
            return null;
        });
        System.out.println(Thread.currentThread().getName() + "------主线程先去做其他事情");
    }
}