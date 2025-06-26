package com.ramyjoo.fashionstore.dto;

import com.ramyjoo.fashionstore.model.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private USER_ROLE role;
}

