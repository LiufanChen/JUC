package com.clf.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: CompletableFutureApi2Demo
 * Package: com.clf.CompletableFuture
 * Description:
 *   接受任务的处理结果，并消费处理，无返回结果
 *   thenAccept
 *
 * @Author clf
 * @Create 2025/6/5 18:19
 * @Version 1.0
 */
public class CompletableFutureApi2Demo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() -> {
            return 1;
        }, threadPool).thenApply(f -> {
            return f + 2;
        }).thenApply(f -> {
            return f + 2;
        }).thenAccept(r -> {
            System.out.println(r);//5
        });
    }
}