package com.ramyjoo.fashionstore.repository;

import com.ramyjoo.fashionstore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
