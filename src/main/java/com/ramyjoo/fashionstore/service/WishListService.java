package com.ramyjoo.fashionstore.service;

import com.ramyjoo.fashionstore.dto.ProductResponseDTO;
import com.ramyjoo.fashionstore.exceptions.ResourceNotFoundException;
import com.ramyjoo.fashionstore.model.Product;
import com.ramyjoo.fashionstore.model.User;
import com.ramyjoo.fashionstore.model.UserPrincipal;
import com.ramyjoo.fashionstore.model.WishlistItem;
import com.ramyjoo.fashionstore.repository.ProductRepository;
import com.ramyjoo.fashionstore.repository.UserRepository;
import com.ramyjoo.fashionstore.repository.WishListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishlistRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public void addToWishlist(Long productId) {
        User user = getCurrentUser();
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (wishlistRepo.existsByUserAndProduct(user, product)) {
            throw new IllegalStateException("Product already in wishlist");
        }

        WishlistItem item = new WishlistItem();
        item.setUser(user);
        item.setProduct(product);
        item.setAddedAt(LocalDateTime.now());

        wishlistRepo.save(item);
    }

    public void removeFromWishlist(Long productId) {
        User user = getCurrentUser();
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        wishlistRepo.deleteByUserAndProduct(user, product);
    }

    public List<ProductResponseDTO> getWishlist() {
        User user = getCurrentUser();
        return wishlistRepo.findByUser(user)
                .stream()
                .map(w -> modelMapper.map(w.getProduct(), ProductResponseDTO.class))
                .toList();
    }

    private User getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findByEmail(userPrincipal.getUsername());
    }
}

