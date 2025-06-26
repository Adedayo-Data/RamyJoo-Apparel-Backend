package com.ramyjoo.fashionstore.controller;

import com.ramyjoo.fashionstore.dto.OrderResponseDTO;
import com.ramyjoo.fashionstore.model.Orders;

import com.ramyjoo.fashionstore.model.User;
import com.ramyjoo.fashionstore.model.UserPrincipal;
import com.ramyjoo.fashionstore.repository.UserRepository;
import com.ramyjoo.fashionstore.service.CartService;
import com.ramyjoo.fashionstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    // 🔍 Get all orders for the logged-in user
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrdersForUser();
        return ResponseEntity.ok(orders);
    }

    // 🔍 Get a single order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long orderId) {
        OrderResponseDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDTO> checkout(Authentication auth) {
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        User user = userRepo.findByEmail(principal.getUsername());

        Orders order = cartService.checkoutCart(user);
        OrderResponseDTO response = modelMapper.map(order, OrderResponseDTO.class);

        return ResponseEntity.ok(response);
    }

}

