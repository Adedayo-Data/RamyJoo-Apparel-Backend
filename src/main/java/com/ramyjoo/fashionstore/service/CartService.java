package com.ramyjoo.fashionstore.service;

import com.ramyjoo.fashionstore.dto.CartResponseDTO;
import com.ramyjoo.fashionstore.dto.OrderResponseDTO;
import com.ramyjoo.fashionstore.exceptions.ResourceNotFoundException;
import com.ramyjoo.fashionstore.model.*;
import com.ramyjoo.fashionstore.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final ProductRepository productRepo;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepo;

    public CartResponseDTO getUserCart(String userEmail) {
        Cart cart = cartRepo.findByUserEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        return mapToCartResponseDTO(cart);
    }

    public CartResponseDTO addItemToCart(String userEmail, Long productId, int quantity) {

        Cart cart = new Cart();
        Optional<Cart> optionalCart = cartRepo.findByUserEmail(userEmail);

        if(optionalCart.isPresent()){
            cart = optionalCart.get();
        }

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        System.out.println("Product object: " + product.getProductName());
        Optional<CartItem> existingItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setPriceAtPurchase(product.getPrice()); // optional price snapshot
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setPriceAtPurchase(product.getPrice());

            cart.getCartItems().add(newItem);
        }

        cart.calculateTotal();
        Cart updatedCart = cartRepo.save(cart);
        System.out.println("Cart Item now: " + updatedCart.getCartItems());
        return modelMapper.map(updatedCart, CartResponseDTO.class);
    }

    public CartResponseDTO updateItemQuantity(String userEmail, Long productId, int newQuantity) {
        System.out.println("Update Item Quantity: " + userEmail);
        System.out.println("Update Item Quantity: " + productId);
        System.out.println("Update Item Quantity: " + newQuantity);
        Cart cart = cartRepo.findByUserEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        CartItem item = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        item.setQuantity(newQuantity);
        cart.calculateTotal();
        Cart updatedCart = cartRepo.save(cart);
        return modelMapper.map(updatedCart, CartResponseDTO.class);
    }

    public void removeItem(String userEmail, Long productId) {
        Cart cart = cartRepo.findByUserEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cart.calculateTotal();
        cartRepo.save(cart);
    }

    public void clearCart(String userEmail) {
        Cart cart = cartRepo.findByUserEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        cart.getCartItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepo.save(cart);
    }

    // TODO: implement this method
    private CartResponseDTO mapToCartResponseDTO(Cart cart) {
        return modelMapper.map(cart, CartResponseDTO.class);
    }

    @Transactional
    public Orders checkoutCart(User user) {
        Cart cart = user.getCart();
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        // Create new order
        Orders order = new Orders();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(STATUS.PENDING);

        List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
            OrderItem item = new OrderItem();
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            item.setPriceAtPurchase(cartItem.getProduct().getPrice()); // snapshot
            item.setTotalPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            item.setOrder(order);
            return item;
        }).toList();

        order.setOrderItems(orderItems);

        BigDecimal total = orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(total);

        // Optionally add delivery address
//        if (user.getDeliveryAddress() != null) {
//            order.setDeliveryAddress(user.getDeliveryAddress());
//        }

        // Save order
        orderRepo.save(order);

        // Clear cart
        cartItemRepo.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepo.save(cart);

        return order;
    }

}

