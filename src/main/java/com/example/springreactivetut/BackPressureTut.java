package com.example.springreactivetut;

import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class BackPressureTut {

    private Flux<Long> createNoOverflow() {
        return Flux.range(1,Integer.MAX_VALUE).log()
                .concatMap(x-> Mono.delay(Duration.ofMillis(100)));
    }

    private Flux<Long> createOverflow() {
        return Flux.interval(Duration.ofMillis(1)).log()
                .concatMap(x-> Mono.delay(Duration.ofMillis(100)));
    }
    private Flux<Long> dropRequestWhenOverflow() {
        return Flux.interval(Duration.ofMillis(1))
                .onBackpressureDrop()
                .concatMap(x->Mono.delay(Duration.ofMillis(100)).thenReturn(x))
                .doOnNext(x-> System.out.println("elements taken:"+x));
    }

    private Flux<Long> bufferRequestWhenOverflow() {
        return Flux.interval(Duration.ofMillis(1))
                .onBackpressureBuffer(67, BufferOverflowStrategy.DROP_LATEST)
                .concatMap(x->Mono.delay(Duration.ofMillis(100)).thenReturn(x))
                .doOnNext(x-> System.out.println("elements taken:"+x));
    }

    public static void main(String[] args) {
        BackPressureTut backPressureTut = new BackPressureTut();
        // This will not cause overflow the reason being we are getting data only when we are requesting which means
        // we can handle the data then only we are requesting
//        backPressureTut.createNoOverflow().blockLast();
        try{
            // overflow will happen because data is getting emitted without subscriber sending request
            // data coming every 1 millisecond but subscriber taking 100 millisecond to process so overflow
//            backPressureTut.createOverflow().blockLast();
//            backPressureTut.dropRequestWhenOverflow().blockLast();
            backPressureTut.bufferRequestWhenOverflow().blockLast();
        }
        catch (Exception e){
            System.out.println("E:"+e.getMessage());
        }
    }
}
