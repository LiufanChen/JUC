package com.clf.CompletableFuture;

import java.util.concurrent.*;

/**
 * ClassName: FutureApiDemo
 * Package: com.clf.CompletableFuture
 * Description:
 *
 * @Author clf
 * @Create 2025/6/5 13:15
 * @Version 1.0
 */

// 优点：Future+线程池异步多线程任务配合，能显著提高程序的运行效率。
// 缺点：
//    get()阻塞---一旦调用get()方法求结果，一旦调用不见不散，非要等到结果才会离开，不管你是否计算完成，如果没有计算完成容易程序堵塞。
//    isDone()轮询---轮询的方式会耗费无谓的cpu资源，而且也不见得能及时得到计算结果，如果想要异步获取结果，通常会以轮询的方式去获取结果，尽量不要阻塞。
//    结论：Future对于结果的获取不是很友好，只能通过阻塞或轮询的方式得到任务的结果。

public class FutureApiDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + "--------come in");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task over";
        });

        Thread t1 = new Thread(futureTask, "t1");
        t1.start();

//        System.out.println(futureTask.get());//这样会有阻塞的可能，在程序没有计算完毕的情况下。
        System.out.println(Thread.currentThread().getName() + " ------忙其他任务");
//        System.out.println(futureTask.get(3,TimeUnit.SECONDS));//只愿意等待三秒，计算未完成直接抛出异常
        while (true) {//轮询
            if(futureTask.isDone()){
                System.out.println(futureTask.get());
                break;
            }else{
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("正在处理中，不要催了，越催越慢");
            }
        }

    }
}