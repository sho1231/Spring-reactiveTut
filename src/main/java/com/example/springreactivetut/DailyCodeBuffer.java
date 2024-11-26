package com.example.springreactivetut;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class DailyCodeBuffer {
    
    private Flux<String> flatMapAsync() {
        Flux<String> flux = Flux.fromIterable(List.of("Orange","Mango","Banana"));
        return flux.flatMap((d)->Flux.just(d.split("")).delayElements(Duration.ofMillis(new Random().nextInt(1000))))

                .log();
    }

    private Flux<String> concatMapAsync() {
        Flux<String> flux = Flux.fromIterable(List.of("Orange","Mango","Banana"));
        return flux.concatMap((d)->Flux.just(d.split("")).delayElements(Duration.ofMillis(new Random().nextInt(1000))))

                .log();
    }
    private Flux<String> flatMapMany() {
        Mono<String> mono = Mono.just("mango");
        return mono.flatMapMany((d)->Flux.just(d.split(""))).log();
    }

    private Flux<String> exampleTransform() {
        Function<Flux<String>,Flux<String>> filterFunction = data -> data.filter(words->words.length()==4);
        return Flux.just("Word","Abcd","a").transform(filterFunction);
    }

    private Flux<String> exampleDefaultIfEmpty() {
        Function<Flux<String>,Flux<String>> filterFunction = data -> data.filter(words->words.length()==4);
        return Flux.just("Wor","Acd","a").transform(filterFunction).defaultIfEmpty("Not present");
    }
    private String generateString() {
        System.out.println("generating");
        return "abcd ab";
    }
    private String generateString2() {
        System.out.println("generating123");
        return "wass ab";
    }
    private Flux<String> exampleSwitchIfEmpty() {
        Function<Flux<String>,Flux<String>> filterFunction = data -> data.filter(words->words.length()==4);
        return Flux.fromIterable(Arrays.stream(generateString2().split(" ")).toList()).transform(filterFunction).switchIfEmpty(
                Flux.defer(()->{
                   return Flux.fromIterable(
                            Arrays.stream(generateString().split(" ")).toList());
                })
        ).map(String::toUpperCase);
    }
    private Flux<String> exampleSwitchIfEmptyComplex() {
        Function<Flux<String>,Flux<String>> filterFunction = data -> data.filter(words->words.length()==4);
        return Flux.just("Wass","d","a").transform(filterFunction).switchIfEmpty(Flux.just(
                "abc","ab"
        ).transform(filterFunction).defaultIfEmpty("test"));
    }

    public static void main(String[] args) throws InterruptedException {
        DailyCodeBuffer dailyCodeBuffer = new DailyCodeBuffer();
        /*Order of flux is not preserved*/
//        dailyCodeBuffer.flatMapAsync().subscribe(System.out::println);
        /*Order of flux is not preserved*/
//        dailyCodeBuffer.concatMapAsync().subscribe(System.out::println);
//        dailyCodeBuffer.flatMapMany().subscribe(System.out::println);
//        TimeUnit.SECONDS.sleep(7);
        dailyCodeBuffer.exampleSwitchIfEmpty().subscribe(System.out::println);
    }
}
