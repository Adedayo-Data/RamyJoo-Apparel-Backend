package com.ramyjoo.fashionstore.dto;

import com.ramyjoo.fashionstore.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponseDTO {

    private String jwt;
    private USER_ROLE role;
    private String message;


}
