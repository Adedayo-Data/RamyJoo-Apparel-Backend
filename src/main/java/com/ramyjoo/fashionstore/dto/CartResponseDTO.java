package com.ramyjoo.fashionstore.dto;

import com.ramyjoo.fashionstore.model.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartResponseDTO {

    private List<CartItem> cartItems = new ArrayList<>();
    private BigDecimal totalAmount;
}
