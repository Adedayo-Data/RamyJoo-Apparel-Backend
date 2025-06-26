package com.ramyjoo.fashionstore.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductResponseDTO {

    private Long id;
    private String productName; // 👈 this maps from product.productName
    private String category; // 👈 maps from product.subCategory.subCategoryName
    private String description;
    private List<String> aboutItem; // we’ll keep it null or empty for now (you don’t have it in Product)
    private BigDecimal price;
    private int discount; // you don’t have this yet — set to 0
    private float rating;   // you don’t have this yet — set to 0
    private List<ReviewDTO> reviews = new ArrayList<>(); // you don’t have this — set to empty list
    private String brand;
    private List<String> color;
    private int stockItems; // you don’t have this — set to 0
    private List<String> images;

    // Getters and Setters
}
