package com.ramyjoo.fashionstore.repository;

import com.ramyjoo.fashionstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
