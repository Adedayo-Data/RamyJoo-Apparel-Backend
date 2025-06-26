package com.ramyjoo.fashionstore.repository;

import com.ramyjoo.fashionstore.model.Orders;
import com.ramyjoo.fashionstore.model.STATUS;
import com.ramyjoo.fashionstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    long countByCreatedAtAfter(LocalDateTime dateTime);
    long countByStatus(STATUS status);

    List<Orders> findByUser(User user);
}
