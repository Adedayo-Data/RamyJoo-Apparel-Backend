package com.ramyjoo.fashionstore.controller.admin;

import com.ramyjoo.fashionstore.repository.OrderRepository;
import com.ramyjoo.fashionstore.repository.ProductRepository;
import com.ramyjoo.fashionstore.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminStatsController {

    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final OrderRepository OrderRepo;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats(){
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", productRepo.count());
        stats.put("totalUsers", userRepo.count());
        stats.put("totalOrders", OrderRepo.count());
        stats.put("totalSales", OrderRepo.sumTotalSales());

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/stats/products-per-month")
    public ResponseEntity<List<Map<String, Object>>> getProductsPerMonth() {
        List<Object[]> result = productRepo.countProductsByMonth();

        List<Map<String, Object>> data = result.stream().map(obj -> {
            Map<String, Object> entry = new HashMap<>();
            entry.put("month", obj[0]); // e.g., "Jan", "Feb"
            entry.put("products", obj[1]); // count
            return entry;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(data);
    }

}
