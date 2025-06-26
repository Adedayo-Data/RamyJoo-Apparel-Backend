package com.ramyjoo.fashionstore.dto;

import com.ramyjoo.fashionstore.model.Address;
import com.ramyjoo.fashionstore.model.STATUS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private BigDecimal totalPrice;
    private String status;
    private List<OrderItemResponseDTO> orderItem;
    private Address deliveryAddress;
    private String customerName;

    public <R> OrderResponseDTO(Long id, LocalDateTime createdAt, BigDecimal totalPrice, STATUS status, R collect) {

    }
}



