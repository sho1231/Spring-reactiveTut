package com.example.springreactivetut;

//https://www.youtube.com/watch?v=eupNfdKMFL4

import reactor.core.publisher.Mono;

public class DeferExample {

    public static void test() throws InterruptedException {
        Mono<Long> nonDefer = Mono.just(System.currentTimeMillis());
        Mono<Long> defer = Mono.defer(()->Mono.just(System.currentTimeMillis()));
        nonDefer.subscribe((data)-> System.out.println("nonDefer:"+data));
        Thread.sleep(2000);
        nonDefer.subscribe((data)-> System.out.println("nonDefer:"+data));
        Thread.sleep(2000);
        defer.subscribe((data)-> System.out.println("defer:"+data));
        Thread.sleep(2000);
        defer.subscribe((data)-> System.out.println("defer:"+data));
    }
    public static void main(String[] args) throws InterruptedException {
        DeferExample.test();
    }
}
