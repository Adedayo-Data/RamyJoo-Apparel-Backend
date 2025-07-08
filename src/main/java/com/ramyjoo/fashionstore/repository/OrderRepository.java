package com.ramyjoo.fashionstore.repository;

import com.ramyjoo.fashionstore.model.Orders;
import com.ramyjoo.fashionstore.model.STATUS;
import com.ramyjoo.fashionstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    long countByCreatedAtAfter(LocalDateTime dateTime);
    long countByStatus(STATUS status);

    List<Orders> findByUser(User user);

    @Query("SELECT SUM(o.totalPrice) FROM Orders o")
    BigDecimal sumTotalSales();
}
