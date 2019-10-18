package com.webflux.app.controller.impl;

import com.webflux.app.controller.BillingControllerRest;
import com.webflux.app.dao.BillingDao;
import com.webflux.app.model.entity.Billing;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Slf4j
public class BillingControllerRestImpl implements BillingControllerRest {

    private BillingDao billingDao;

    public BillingControllerRestImpl(BillingDao billingDao){
        this.billingDao = billingDao;
    }

    @Override
    public Flux<Billing> getBillings() {
        Flux<Billing> billings = billingDao.findAll().map(item -> {
            item.setAmount(50);
            return item;
        }).doOnNext(item -> log.info(item.getAmount().toString()));
        return billings;
    }

    @Override
    public Mono<Billing> getBillingById(String id) {
        Flux<Billing> billingFlux = billingDao.findAll();
        Mono<Billing> billingMono = billingFlux.filter(item -> item.getId().equals(id)).next().doOnNext(item -> log.info(item.getAmount().toString()));
        return billingMono;
        //return  billingDao.findById(id).doOnNext(item -> log.info(item.getId()));
    }
}
