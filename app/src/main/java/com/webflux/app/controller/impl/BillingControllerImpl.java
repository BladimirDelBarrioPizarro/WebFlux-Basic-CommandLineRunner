package com.webflux.app.controller.impl;

import com.webflux.app.dao.BillingDao;
import com.webflux.app.model.entity.Billing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@Controller
public class BillingControllerImpl{
    private BillingDao billingDao;

    @Autowired
    public BillingControllerImpl(BillingDao billingDao){
        this.billingDao=billingDao;
    }

    @GetMapping({"/listar","/"})
    public String list(Model model) {
        Flux<Billing> billingFlux = billingDao.findAll();
        model.addAttribute("billings",billingFlux);
        model.addAttribute("title","Flux billing");
        return "list";
    }
}
