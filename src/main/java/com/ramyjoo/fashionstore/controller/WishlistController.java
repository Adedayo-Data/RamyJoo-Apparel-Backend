package com.ramyjoo.fashionstore.controller;

import com.ramyjoo.fashionstore.dto.ProductResponseDTO;
import com.ramyjoo.fashionstore.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishListService wishlistService;

    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addToWishlist(@PathVariable Long productId) {
        wishlistService.addToWishlist(productId);
        return ResponseEntity.ok("Added to wishlist");
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long productId) {
        wishlistService.removeFromWishlist(productId);
        return ResponseEntity.ok("Removed from wishlist");
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getWishlist() {
        return ResponseEntity.ok(wishlistService.getWishlist());
    }
}

