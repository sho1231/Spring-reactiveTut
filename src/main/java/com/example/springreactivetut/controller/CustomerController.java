package com.example.springreactivetut.controller;

import com.example.springreactivetut.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;
    @GetMapping("/")
    public String test() {
        return "test";
    }

    @PostMapping("/save")
    // here internally before save method is called customer will be received as mono and then webflux will convert it
    // to a normal object
    public Mono<Customer> save(@RequestBody Customer customer) {
        return reactiveMongoTemplate.save(customer);
    }

    @GetMapping("/findById/{id}")
    public Mono<Customer> findById (@PathVariable String id) {
        return reactiveMongoTemplate.findById(id, Customer.class);
    }
}
