package com.ramyjoo.fashionstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDTO {
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalPrice; // unitPrice * quantity
}

