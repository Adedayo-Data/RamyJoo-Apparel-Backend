package com.ramyjoo.fashionstore.controller;

import com.ramyjoo.fashionstore.dto.CartItemRequestDTO;
import com.ramyjoo.fashionstore.dto.CartResponseDTO;
import com.ramyjoo.fashionstore.model.User;
import com.ramyjoo.fashionstore.model.UserPrincipal;
import com.ramyjoo.fashionstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Get cart for current user
    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CartResponseDTO cart = cartService.getUserCart(userPrincipal.getUsername());
        return ResponseEntity.ok(cart);
    }

    // Add item to cart
    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(@RequestBody CartItemRequestDTO request) {
        System.out.println("Add to cart hit");
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userPrincipal.getUsername());
        CartResponseDTO updatedCart = cartService.addItemToCart(userPrincipal.getUsername(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    // Update item quantity
    @PutMapping("/update")
    public ResponseEntity<CartResponseDTO> updateItemQuantity(@RequestBody CartItemRequestDTO request) {
        System.out.println("updateItemQYT to cart hit");
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CartResponseDTO updatedCart = cartService.updateItemQuantity(userPrincipal.getUsername(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    // Remove item from cart
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<String> removeItem(@PathVariable Long itemId) {
        System.out.println("REMOVE from cart hit");
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cartService.removeItem(userPrincipal.getUsername(), itemId);
        return ResponseEntity.ok("Item removed successfully!");
    }

    // Clear entire cart
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        System.out.println("clear cart hit");
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cartService.clearCart(userPrincipal.getUsername());
        return ResponseEntity.ok("Cart cleared successfully");
    }
}

