package com.ramyjoo.fashionstore.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;

    private OperationType operation;
}
