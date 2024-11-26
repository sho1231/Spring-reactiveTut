package com.example.springreactivetut;

//https://www.youtube.com/watch?v=eupNfdKMFL4

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.function.Function;

public class DeferExample {
    private String generateString() {
        System.out.println("generating");
        return "abcd ab";
    }
    private String generateString2() {
        System.out.println("generating123");
        return "wass ab";
    }
    private Flux<String> exampleSwitchIfEmptyNonDefer() {
        Function<Flux<String>,Flux<String>> filterFunction = data -> data.filter(words->words.length()==4);
        return Flux.fromIterable(Arrays.stream(generateString2().split(" ")).toList()).transform(filterFunction).switchIfEmpty(
                Flux.fromIterable(
                        Arrays.stream(generateString().split(" ")).toList())
        ).map(String::toUpperCase);
    }

    private Flux<String> exampleSwitchIfEmptyDefer() {
        Function<Flux<String>,Flux<String>> filterFunction = data -> data.filter(words->words.length()==4);
        return Flux.fromIterable(Arrays.stream(generateString2().split(" ")).toList()).transform(filterFunction).switchIfEmpty(
                Flux.defer(()-> Flux.fromIterable(
                        Arrays.stream(generateString().split(" ")).toList()))
        ).map(String::toUpperCase);
    }
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
//        DeferExample.test();
        // one more example
        DeferExample deferExample = new DeferExample();
        // for this we can see that even though transform function is not retuning empty flux, still
        // generating() is getting executed
//        deferExample.exampleSwitchIfEmptyNonDefer();
        // here we can see since defer is used so generating() is not executed unless being subscribed
        // that is if transform return a empty flux
//        deferExample.exampleSwitchIfEmptyDefer().subscribe(System.out::println);

    }
}
