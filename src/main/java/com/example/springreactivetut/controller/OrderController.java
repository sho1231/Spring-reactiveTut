package com.example.springreactivetut.controller;


import com.example.springreactivetut.model.Customer;
import com.example.springreactivetut.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @PostMapping("/save")
    public Mono<Order> saveOrder(@RequestBody Order order) {
        return reactiveMongoTemplate.save(order);
    }

    @GetMapping("/generate-report")
    public Mono<Map<String,Double>> generateReport() {
        return reactiveMongoTemplate.findAll(Customer.class)
                .flatMap(customer -> Mono.zip(Mono.just(customer.getName()),fetchAndCalculateByCustomerId(customer.getId())))
                .collectMap(Tuple2::getT1, Tuple2::getT2);
    }

    public Mono<Double> fetchAndCalculateByCustomerId(String customerId) {
        Criteria criteria = Criteria.where("customerId").is(customerId);
        return reactiveMongoTemplate.find(Query.query(criteria),Order.class)
                .map(Order::getTotal)
                .reduce(0d,Double::sum);
    }

    @GetMapping("/generate-report/user/{userId}")
    public Mono<Map<String,Double>> generateReportForUser(@PathVariable String userId) {
        Criteria criteria = Criteria.where("customerId").is(userId);
        return reactiveMongoTemplate.find(Query.query(criteria),Order.class)
                .map((c)->{
                    Map<String,Double> result = new HashMap<>();
                    result.put("total", c.getTotal());
                    result.put("discount" , c.getDiscount());
                    result.put("revenue", c.getTotal()-c.getDiscount());
                    return result;
                }).collectList().map(d->d.get(0));
    }
}
