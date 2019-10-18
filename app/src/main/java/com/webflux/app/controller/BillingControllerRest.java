package com.webflux.app.controller;


import com.webflux.app.model.entity.Billing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api")
public interface BillingControllerRest {
    @GetMapping(path = "/billing")
    Flux<Billing> getBillings();

    @GetMapping(path = "/billing/{id}")
    Mono<Billing> getBillingById(@PathVariable("id") String id);
}
