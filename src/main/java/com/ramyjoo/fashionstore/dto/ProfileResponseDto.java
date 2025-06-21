package com.ramyjoo.fashionstore.dto;

import com.ramyjoo.fashionstore.model.Address;
import com.ramyjoo.fashionstore.model.USER_ROLE;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class ProfileResponseDto {

    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private USER_ROLE role;
    private Address deliveryAddress; //UI SHOULD HAVE THIS LIKE CREATE, UPDATE DELETE ADDRESS
    private String profilePhoto;

}
