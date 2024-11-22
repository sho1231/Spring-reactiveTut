package com.example.springreactivetut;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Test {
    public static Flux<Integer> getFlux() {
        return Flux.range(1,20);
    }
    public static Map<String,String> func1() throws InterruptedException {
        System.out.println("bca");
        TimeUnit.SECONDS.sleep(2);
        return Map.of("apple","20.0","orange","30.0");
    }
    public static Map<String,String> func2() throws InterruptedException {
        System.out.println("abc");
        TimeUnit.SECONDS.sleep(2);
        return Map.of("banana","10.0","cucumber","30.0");
    }

    public static Flux<Map<String, String>> fluxFlatMapExample() throws InterruptedException {


        return Flux.just(func1(),func2()).flatMap((d)->{
            return Flux.just(d)//.delayElements(Duration.ofSeconds(2))
                    ;
        });
    }
    public static Flux<Map<String, String>> fluxExample() throws InterruptedException {


        return Flux.just(func1(),func2()).flatMap((d)->{
            return Flux.just(d)//.delayElements(Duration.ofSeconds(2))
                    ;
        });
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        fluxExample().subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(5);
        long endTime = System.currentTimeMillis();
        System.out.println("time:"+(endTime-startTime)/1000);
    }
}
