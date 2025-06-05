package com.clf.CompletableFuture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * ClassName: CompletableFutureMallDemo
 * Package: com.clf.CompletableFuture
 * Description:
 *
 * @Author clf
 * @Create 2025/6/5 13:35
 * @Version 1.0
 */
public class CompletableFutureMallDemo {
    static List<NetMall> list = Arrays.asList(new NetMall("jd"), new NetMall("taobao"), new NetMall("dangdang"));

    /**
     * step by step
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPrice(List<NetMall> list, String productName) {
        //《Mysql》 in jd price is 88.05
        return list
                .stream()
                .map(netMall ->
                        String.format("《" + productName + "》" + "in %s price is %.2f",
                                netMall.getNetMallName(),
                                netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    /**
     * all in
     * 把list里面的内容映射给CompletableFuture()
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list.stream().map(netMall ->
                        CompletableFuture.supplyAsync(() ->
                                String.format("《" + productName + "》" + "in %s price is %.2f",
                                        netMall.getNetMallName(),
                                        netMall.calcPrice(productName)))) //Stream<CompletableFuture<String>>
                .collect(Collectors.toList()) //List<CompletableFuture<String>>
                .stream()//Stream<String>
                .map(s -> s.join()).collect(Collectors.toList()); //List<String>
    }

    public static void main(String[] args) {
        /**
         * 采用step by setp方式查询
         * 《masql》in jd price is 110.11
         * 《masql》in taobao price is 109.32
         * 《masql》in dangdang price is 109.24
         * ------costTime: 3094 毫秒
         */
        long StartTime = System.currentTimeMillis();
        List<String> list1 = getPrice(list, "masql");
        for (String element : list1) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("------costTime: " + (endTime - StartTime) + " 毫秒");

        /**
         * 采用 all in三个异步线程方式查询
         * 《mysql》in jd price is 109.71
         * 《mysql》in taobao price is 110.69
         * 《mysql》in dangdang price is 109.28
         * ------costTime1009 毫秒
         */
        long StartTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByCompletableFuture(list, "mysql");
        for (String element : list2) {
            System.out.println(element);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("------costTime" + (endTime2 - StartTime2) + " 毫秒");

    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class NetMall {
    private String netMallName;

    public double calcPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}