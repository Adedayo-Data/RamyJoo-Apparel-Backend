package com.ramyjoo.fashionstore.repository;

import com.ramyjoo.fashionstore.model.Product;
import com.ramyjoo.fashionstore.model.User;
import com.ramyjoo.fashionstore.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUser(User user);
    boolean existsByUserAndProduct(User user, Product product);
    void deleteByUserAndProduct(User user, Product product);
}

