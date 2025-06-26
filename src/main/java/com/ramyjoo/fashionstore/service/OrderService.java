package com.ramyjoo.fashionstore.service;


import com.ramyjoo.fashionstore.dto.OrderItemResponseDTO;
import com.ramyjoo.fashionstore.dto.OrderResponseDTO;
import com.ramyjoo.fashionstore.exceptions.ResourceNotFoundException;
import com.ramyjoo.fashionstore.model.OrderItem;
import com.ramyjoo.fashionstore.model.Orders;
import com.ramyjoo.fashionstore.model.UserPrincipal;
import com.ramyjoo.fashionstore.repository.OrderRepository;
import com.ramyjoo.fashionstore.repository.UserRepository;
import com.ramyjoo.fashionstore.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    // Get current authenticated user
    private User getAuthenticatedUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userRepo.findByEmail(userPrincipal.getUsername());
    }

    // Get all orders for current user
    public List<OrderResponseDTO> getAllOrdersForUser() {
        User user = getAuthenticatedUser();
        List<Orders> orders = orderRepo.findByUser(user);

        // Map to DTOs
        return orders.stream()
                .map(order -> mapToDTO(order))
                .collect(Collectors.toList());
    }

    // Get one order by ID (must belong to current user)
    public OrderResponseDTO getOrderById(Long orderId) {
        User user = getAuthenticatedUser();
        Orders order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You can't access this order");
        }

        return mapToDTO(order);
    }

    // 🧠 Helper: Convert Order to OrderResponseDTO
    private OrderResponseDTO mapToDTO(Orders order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getCreatedAt(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getOrderItems().stream()
                        .map(this::mapOrderItemToDTO)
                        .collect(Collectors.toList())
        );
    }

    // 🧠 Helper: Convert OrderItem to DTO
    private OrderItemResponseDTO mapOrderItemToDTO(OrderItem item) {
        return new OrderItemResponseDTO(
                item.getProduct().getId(),
                item.getProduct().getProductName(),
                item.getQuantity(),
                item.getTotalPrice()
        );
    }
}


