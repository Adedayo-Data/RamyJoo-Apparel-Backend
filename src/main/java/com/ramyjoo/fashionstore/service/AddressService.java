package com.ramyjoo.fashionstore.service;

import com.ramyjoo.fashionstore.dto.AddressDTO;
import com.ramyjoo.fashionstore.exceptions.ResourceNotFoundException;
import com.ramyjoo.fashionstore.model.Address;
import com.ramyjoo.fashionstore.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepo;


    //TO FIX: Organize with DTO
    public Address addAddress(Address address){
        System.out.println(address.toString());
        return addressRepo.save(address);
    }

    // update Address
    public void updateAddress(AddressDTO addressDTO){
        Address existing = addressRepo.findById(addressDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        updateAddressEntity(existing, addressDTO);
        addressRepo.save(existing);
    }

    //Delete Address
    // Add later
    private void updateAddressEntity(Address existing, AddressDTO dto) {
        if (dto.getStreet() != null) existing.setStreet(dto.getStreet());
        if (dto.getCity() != null) existing.setCity(dto.getCity());
        if (dto.getState() != null) existing.setState(dto.getState());
        if (dto.getCountry() != null) existing.setCountry(dto.getCountry());
        if (dto.getZipcode() != null) existing.setZipcode(dto.getZipcode());
    }
}
