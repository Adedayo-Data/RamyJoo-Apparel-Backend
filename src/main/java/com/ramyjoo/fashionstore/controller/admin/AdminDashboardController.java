package com.ramyjoo.fashionstore.controller.admin;

import com.ramyjoo.fashionstore.dto.AdminDashboardSummaryDTO;
import com.ramyjoo.fashionstore.model.Orders;
import com.ramyjoo.fashionstore.model.STATUS;
import com.ramyjoo.fashionstore.repository.OrderRepository;
import com.ramyjoo.fashionstore.repository.ProductRepository;
import com.ramyjoo.fashionstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin/summary")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    @GetMapping
    public ResponseEntity<AdminDashboardSummaryDTO> getDashboardSummary() {
        long totalUsers = userRepo.count();
        long totalProducts = productRepo.count();
        long totalOrders = orderRepo.count();

        BigDecimal totalRevenue = orderRepo.findAll().stream()
                .map(Orders::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long ordersToday = orderRepo.countByCreatedAtAfter(LocalDate.now().atStartOfDay());
        long pendingOrders = orderRepo.countByStatus(STATUS.PENDING);

        AdminDashboardSummaryDTO summary = new AdminDashboardSummaryDTO(
                totalUsers,
                totalProducts,
                totalOrders,
                totalRevenue,
                ordersToday,
                pendingOrders
        );

        return ResponseEntity.ok(summary);
    }
}

