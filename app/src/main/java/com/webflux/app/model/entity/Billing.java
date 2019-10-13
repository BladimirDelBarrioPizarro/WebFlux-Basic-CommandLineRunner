package com.webflux.app.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Setter
@Getter
@Document("billing")
public class Billing {
    @Id
    private String id;
    private Date date;
    private Integer patientId;
    private Integer sessionId;
    private Integer amount;

    public Billing(Integer patientId,Integer sessionId,Integer amount){
        this.patientId=patientId;
        this.sessionId=sessionId;
        this.amount=amount;
    }
}
