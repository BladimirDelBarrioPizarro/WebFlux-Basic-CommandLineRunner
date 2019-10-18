package com.webflux.app.controller.impl;

import com.webflux.app.dao.BillingDao;
import com.webflux.app.model.entity.Billing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Controller
public class BillingControllerImpl{
    private BillingDao billingDao;

    @Autowired
    public BillingControllerImpl(BillingDao billingDao){
        this.billingDao=billingDao;
    }

    @GetMapping({"/listar","/"})
    public String list(Model model) {
        Flux<Billing> billingFlux = billingDao.findAll().map(item -> {
            return item;
        });
        billingFlux.subscribe(item -> log.info(String.valueOf(item.getDate())));
        model.addAttribute("billings",billingFlux);
        model.addAttribute("title","Flux billing");
        return "list";
    }


    // ReactiveDataDriverContextVariable contrapresión los datos se mostrarán de uno en uno
    @GetMapping(path = "/dataDriverList")
    public String listDataDriver(Model model) {
        Flux<Billing> billingFlux = billingDao.findAll().map(item -> {
            return item;
        }).delayElements(Duration.ofSeconds(5));

        model.addAttribute("billings",new ReactiveDataDriverContextVariable(billingFlux,1));
        model.addAttribute("title","Flux billing");
        return "list";
    }

    // Chunked Tamaño del buffer (paginación)
    @GetMapping(path = "/chunked")
    public String listDataChunked(Model model) {
        Flux<Billing> billingFlux = billingDao.findAll().map(item -> {
            return item;
        }).repeat(1000);

        model.addAttribute("billings",new ReactiveDataDriverContextVariable(billingFlux,1));
        model.addAttribute("title","Flux billing");
        return "list";
    }


}
