package com.ramyjoo.fashionstore.dto;

import com.ramyjoo.fashionstore.model.Address;
import com.ramyjoo.fashionstore.model.USER_ROLE;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProfileRequestDTO {

    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private USER_ROLE role;
    private AddressDTO deliveryAddress;
    private String profilePhoto;

}
