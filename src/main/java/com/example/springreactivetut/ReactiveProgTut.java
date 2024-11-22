package com.example.springreactivetut;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.time.Duration;
import java.util.List;
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
      return Flux.fromIterable(List.of("Hello, World!","Hi Shourja")).log();
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
    List<Integer> list = reactiveProgTut.collectListExample().block();
    System.out.println("List:"+list);
  }
}
