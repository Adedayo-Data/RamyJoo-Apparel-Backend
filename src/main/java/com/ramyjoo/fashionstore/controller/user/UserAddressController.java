package com.ramyjoo.fashionstore.controller.user;

import com.ramyjoo.fashionstore.dto.AddressDTO;
import com.ramyjoo.fashionstore.dto.ProductResponseDTO;
import com.ramyjoo.fashionstore.model.Address;
import com.ramyjoo.fashionstore.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/address/")
public class UserAddressController {

    @Autowired
    private AddressService service;

    @PostMapping("/create")
    public ResponseEntity<Address> createAddress(Address address){
        Address createdAddress = service.addAddress(address);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateAddress(AddressDTO address){
        service.updateAddress(address);
        return new ResponseEntity<>("Address Updated", HttpStatus.OK);
    }
}
