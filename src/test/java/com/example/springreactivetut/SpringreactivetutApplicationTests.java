package com.example.springreactivetut;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

@SpringBootTest
class SpringreactivetutApplicationTests {

	@Test
	void contextLoads() {
	}

//	private Flux<String>
//private static Mono<Long> nonDeferMono() {
//	return
//}

//	private static Mono<Long> deferMono() {
//		return ;
//	}
	@Test
	public void test() throws InterruptedException {
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

}
