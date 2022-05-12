package com.netease.nim.camellia.tools.samples;

import com.netease.nim.camellia.tools.circuitbreaker.CamelliaCircuitBreaker;
import com.netease.nim.camellia.tools.circuitbreaker.CircuitBreakerConfig;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by caojiajun on 2022/3/31
 */
public class CircuitBreakerSamples {
    public static void main(String[] args) {
        CircuitBreakerConfig config = new CircuitBreakerConfig();
        //以下参数不可以动态配置
//        config.setName("camellia-circuit-breaker");
//        config.setStatisticSlidingWindowTime(10*1000L);//统计成功失败的滑动窗口的大小，单位ms，默认10s
//        config.setStatisticSlidingWindowBucketSize(10);//滑动窗口分割为多少个bucket，默认10个
        //以下参数可以动态配置
//        config.setEnable(() -> true);//熔断器开关，一个lambda表达式，可以动态配置，默认true，若配置false，则所有请求都通过
//        config.setForceOpen(() -> false);//强制打开开关，一个lambda表达式，可以动态配置，默认false，若配置true，则所有请求都通过
//        config.setFailThresholdPercentage(() -> 0.5);////滑动窗口范围内失败比例超过多少触发熔断，默认50%
//        config.setSingleTestIntervalMillis(() -> 5000L);//当熔断器打开的情况下，间隔多久尝试一次探测（也就是半开）
//        config.setRequestVolumeThreshold(() -> 20L);//滑动窗口内至少多少个请求才会触发熔断，默认20个
        CamelliaCircuitBreaker circuitBreaker = new CamelliaCircuitBreaker(config);

        //核心接口就三个，allowRequest、incrementFail、incrementSuccess
        AtomicLong success = new AtomicLong();
        AtomicLong fail = new AtomicLong();
        long start = System.currentTimeMillis();
        new Thread(() -> {
            while (true) {
                if (System.currentTimeMillis() - start <= 10000*2) {
                    //请求之前询问熔断器是否可以执行
                    boolean allowRequest = circuitBreaker.allowRequest();
                    if (!allowRequest) {
                        System.out.println("quick fail of fail");
                        fail.incrementAndGet();
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    long ret = fail.incrementAndGet();
                    if (ret % 100 == 1) {
                        System.out.println("fail=" + ret);
                    }
                    //如果请求失败了，要告诉熔断器
                    circuitBreaker.incrementFail();
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
            System.out.println("end fail");
        }).start();

        new Thread(() -> {
            while (true) {
                //请求之前询问熔断器是否可以执行
                boolean allowRequest = circuitBreaker.allowRequest();
                if (!allowRequest) {
                    System.out.println("quick fail of success");
                    fail.incrementAndGet();
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                long ret = success.incrementAndGet();
                if (ret % 100 == 1) {
                    System.out.println("success=" + ret);
                }
                //如果请求成功了，要告诉熔断器
                circuitBreaker.incrementSuccess();
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
