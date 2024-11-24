package com.example.springreactivetut;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ReactiveProgTut {
  private Mono<String> publisher() {
    // log method will display the process of data going through the subscription
    return Mono.just("Hello, World!").log();
    // justOrEmpty can accept null values though to accept null values this method is not recommended
//    return Mono.justOrEmpty("Hello, World!").log();
    // empty method will return an empty Mono.Recommened method for passing null values
//    return Mono.empty();
  }
  private Flux<String> publisher1() {
    // log method will display the process of data going through the subscription
//    return Flux.just("Hello, World!","Hi Shourja").log();
    // fromIterable method will take a list of values and return a Flux
      return Flux.fromIterable(List.of("Hello, World!","Hi Shourja", "Hi Shourja")).log();
  }
  private Flux<String> mapExample() {
    return Flux.fromIterable(List.of("Hello, World!","Hi Shourja"))
            .map(String::toUpperCase)
            .log();
  }

  private Flux<String> flatMapExample() {
    return Flux.fromIterable(List.of("Hello, World!", "Hi Shourja"))
            .flatMap(value -> Flux.fromArray(value.split("")).delayElements(Duration.ofSeconds(1)))
            .log();
  }

  private Flux<String> skipByCountExample() {
    return Flux.just("Hello, World!", "Hi Shourja").skip(1);
  }
  private Flux<String> skipLastByCountExample() {
    return Flux.just("Hello, World!", "Hi Shourja","a","b","c").skipLast(2);
  }
  private Flux<String> skipByDuration() {
     Flux<String> flux=Flux.just("Hello, World!", "Hi Shourja" ,"Hello","ahdhasd","asdsad")
             .delayElements(Duration.ofSeconds(1));
    return flux.skip(Duration.ofMillis(2020));
  }
  private Flux<Integer> skipWhileExample() {
    // will create items from start <=count
    Flux<Integer> flux=Flux.range(1,20)
//            .skipUntil(n->n<5)
            .skipWhile(n->n>5);
    return flux;
  }
  private Flux<String> concatExample() {
    // order of the flux matters here
    return Flux.concat(Flux.just("a","b"),Flux.just("z","Shourja")).log();
  }
  private Flux<String> mergeExample() {
    // order of the flux matters here
    return Flux.merge(Flux.just("a","b"),Flux.just("z","Shourja")).log();
  }

  private Flux<Tuple3<Integer,Integer,Integer>> zipExample() {
    Flux<Integer> flux1 = Flux.range(1,20)
            .delayElements(Duration.ofSeconds(2));
    Flux<Integer> flux2 = Flux.range(21,20).delayElements(Duration.ofSeconds(1));
    Flux<Integer> flux3 = Flux.range(41,20).delayElements(Duration.ofSeconds(3));
    return Flux.zip(flux1,flux2,flux3);
  }
  private Mono<List<Integer>> collectListExample() {
    Flux<Integer> flux = Flux.range(1,20);
    return flux.collectList();
  }
  private Flux<List<Integer>> bufferBasedOnNoOfData() {
    Flux<Integer> flux = Flux.range(1,20);
    return flux.buffer(3);
  }
  private Flux<List<Integer>> bufferBasedOnDuration() {
    Flux<Integer> flux = Flux.range(1,20)
            .delayElements(Duration.ofSeconds(1));
    return flux.buffer(Duration.ofSeconds(3));
  }

  private Mono<Map<Integer,Integer>> collectMapExample() {
    Flux<Integer> flux = Flux.range(1,20);
    return flux.collectMap((n)->n,(n)->n*n);
  }
  private Flux<Integer> testDoOnEach() {
    return Flux.range(1,20).doOnEach(signal->{
      if(signal.isOnComplete()) {
        System.out.println("complete");
        return;
      }
      System.out.println("value:"+signal.get());
    });
  }
  private Flux<Integer> testDoOnNext() {
    return Flux.range(1,20).doOnNext(System.out::println);
  }

  private Flux<Integer> testDoOnSubscribe() {
    return Flux.range(1,20).doOnSubscribe(subscription -> System.out.println("subscribed"));
  }
  private Flux<Integer> testDoOnCancel() {
    return Flux.range(1,20).delayElements(Duration.ofSeconds(2))
            .doOnCancel(()->System.out.println("Cancelled"));
  }
  private Flux<Integer> testHandleError() {
    return Flux.range(1,20).map((d)->{
      if(d==5) {
       throw new RuntimeException("Can't square this number");
      }
      return d*d;
    }).onErrorContinue(((throwable, o) -> System.out.println("Got exception:"+throwable.getMessage()+" so" +
            " can't square this number:"+o)));
  }
  private Flux<Integer> testHandleError1() {
    return Flux.range(1,20).map((d)->{
      if(d==5) {
        throw new RuntimeException("Can't square this number");
      }
      return d*d;
    }).onErrorReturn(2)
            .onErrorReturn(RuntimeException.class,4);
  }
  private Flux<Integer> testHandleError2() {
    return Flux.range(1,20).map((d)->{
              if(d==5) {
                throw new RuntimeException("Can't square this number");
              }
              return d*d;
            })
            .onErrorResume((throwable)->Mono.just(4));
  }

  private Flux<Integer> testHandleError3() {
    return Flux.range(1,20).map((d)->{
              if(d==5) {
                throw new RuntimeException("Can't square this number");
              }
              return d*d;
            })
            .onErrorMap((throwable)->new Exception(throwable.getMessage()));
  }
  public static void main(String[] args) throws InterruptedException {
      ReactiveProgTut reactiveProgTut = new ReactiveProgTut();
//      reactiveProgTut.publisher().subscribe(System.out::println);
//    reactiveProgTut.publisher1().subscribe(System.out::println);
//    reactiveProgTut.mapExample().subscribe((value) -> System.out.println("value = " + value));
//    reactiveProgTut.flatMapExample().subscribe((value) -> System.out.println("value = " + value));
//    reactiveProgTut.skipByCountExample().subscribe((value) -> System.out.println("value = " + value));
//    reactiveProgTut.skipLastByCountExample().subscribe((value) -> System.out.println("value = " + value));
//    TimeUnit.SECONDS.sleep(40);
//    reactiveProgTut.skipWhileExample().subscribe(System.out::println);
//    reactiveProgTut.concatExample().subscribe(System.out::println);
//    reactiveProgTut.mergeExample().subscribe(System.out::println);
//    reactiveProgTut.zipExample().subscribe(System.out::println);
//    TimeUnit.SECONDS.sleep(60);
//    reactiveProgTut.collectListExample().subscribe(System.out::println);
//    List<Integer> list = reactiveProgTut.collectListExample().block();
//    System.out.println("List:"+list);
//    reactiveProgTut.bufferBasedOnDuration().subscribe(System.out::println);
//    TimeUnit.SECONDS.sleep(40);
//    reactiveProgTut.collectMapExample().subscribe(System.out::println);
//      reactiveProgTut.testDoOnNext().subscribe();
//    reactiveProgTut.testDoOnSubscribe().subscribe(System.out::println);
//    reactiveProgTut.testDoOnSubscribe().subscribe(System.out::println);
//    Disposable disposable = reactiveProgTut.testDoOnCancel().subscribe(System.out::println);
//    TimeUnit.SECONDS.sleep(5);
//    disposable.dispose();
//      reactiveProgTut.testHandleError3().subscribe(System.out::println);
  }
}
