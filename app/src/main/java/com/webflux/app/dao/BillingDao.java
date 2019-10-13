package com.webflux.app.dao;

import com.webflux.app.model.entity.Billing;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BillingDao extends ReactiveMongoRepository<Billing,String> {
}
