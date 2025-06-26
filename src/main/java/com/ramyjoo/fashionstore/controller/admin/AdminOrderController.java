package com.ramyjoo.fashionstore.controller.admin;

import com.ramyjoo.fashionstore.dto.OrderResponseDTO;
import com.ramyjoo.fashionstore.exceptions.ResourceNotFoundException;
import com.ramyjoo.fashionstore.model.Orders;
import com.ramyjoo.fashionstore.model.STATUS;
import com.ramyjoo.fashionstore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

    private final OrderRepository orderRepo;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<Orders> orders = orderRepo.findAll();
        List<OrderResponseDTO> response = orders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        Orders order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        OrderResponseDTO response = modelMapper.map(order, OrderResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam("status") STATUS newStatus
    ) {
        Orders order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        try {
            order.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value");
        }

        orderRepo.save(order);
        return ResponseEntity.ok("Order status updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderRepo.deleteById(id);
        return ResponseEntity.ok("Order deleted successfully");
    }
}

