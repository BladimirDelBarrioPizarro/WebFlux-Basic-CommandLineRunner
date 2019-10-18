package com.webflux.app.boot.config;

import com.webflux.app.controller.BillingControllerRest;
import com.webflux.app.controller.impl.BillingControllerRestImpl;
import com.webflux.app.dao.BillingDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    public BillingControllerRest billingControllerRest(final BillingDao billingDao){
        return new BillingControllerRestImpl(billingDao);
    }
}
