package com.ramyjoo.fashionstore.model;

import lombok.Data;

@Data
public class PaymentRequest {

    private String email;
    private int amount; // in kobo
}
