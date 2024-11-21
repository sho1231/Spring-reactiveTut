package com.example.springreactivetut;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
            .flatMap(value -> Flux.fromArray(value.split("")))
            .log();
  }
  public static void main(String[] args) {
      ReactiveProgTut reactiveProgTut = new ReactiveProgTut();
//      reactiveProgTut.publisher().subscribe(System.out::println);
//    reactiveProgTut.publisher1().subscribe(System.out::println);
//    reactiveProgTut.mapExample().subscribe((value) -> System.out.println("value = " + value));
    reactiveProgTut.flatMapExample().subscribe((value) -> System.out.println("value = " + value));
  }
}
