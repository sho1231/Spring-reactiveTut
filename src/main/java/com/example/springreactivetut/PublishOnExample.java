package com.example.springreactivetut;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class PublishOnExample {
    private Mono<Integer> sameThreadAsCaller()  {
        Mono<Integer> mono = Mono.just(5).map((d)->{
            System.out.println("Thread:"+Thread.currentThread().getName());
            return d;
        });
        return mono;
    }

    private Mono<Integer> differentThreadAsCaller()  {
        Mono<Integer> mono = Mono.just(5)
                .delayElement(Duration.ofSeconds(2))
                .map((d)->{
            System.out.println("Thread:"+Thread.currentThread().getName());
            return d;
        });
        return mono;
    }

    private Mono<Integer> multipleSchedulerPublishOn() {
        return Mono.just(5).log().publishOn(Schedulers.single())
                .map((d)->{
                    System.out.println("Thread:"+Thread.currentThread().getName()+" "+d);
                    return d;
                });
//                .publishOn(Schedulers.boundedElastic())
//                .map((d)->{
//            System.out.println("Thread:"+Thread.currentThread().getName()+" "+d);
//            return d;
//        });
    }
    private ParallelFlux<Integer> multipleSchedulerPublishOn1() {
        return Flux.just(5,6,7,2).log()
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .map((d)->{
                    System.out.println("Thread:"+Thread.currentThread().getName()+" "+d);
                    return d;
                });
//                .publishOn(Schedulers.boundedElastic())
//                .map((d)->{
//            System.out.println("Thread:"+Thread.currentThread().getName()+" "+d);
//            return d;
//        });
    }
    public static void main(String[] args) throws InterruptedException {
        PublishOnExample publishOnExample = new PublishOnExample();
//        publishOnExample.sameThreadAsCaller().subscribe((d)->{
//            System.out.println("Thread:"+Thread.currentThread().getName()
//            +" "+d);
//            return;
//        });
//        publishOnExample.differentThreadAsCaller().subscribe((d)->{
//            System.out.println("Thread:"+Thread.currentThread().getName()
//            +" "+d);
//            return;
//        });
//        TimeUnit.SECONDS.sleep(4);
        publishOnExample.multipleSchedulerPublishOn1().sequential().subscribe(System.out::println);
//        m.subscribe(System.out::println);
        Thread.sleep(2000);

    }
}
