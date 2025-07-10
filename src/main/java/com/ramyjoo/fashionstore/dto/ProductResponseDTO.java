package com.ramyjoo.fashionstore.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductResponseDTO {

    private Long id;
    private String productName;
    private String category; // 👈 maps from product.subCategory.subCategoryName
    private String description;
    private List<String> aboutItem;
    private BigDecimal price;
    private int discount;
    private float rating;
    private List<ReviewDTO> reviews = new ArrayList<>();
    private String brand;
    private List<String> color;
    private List<String> size;
    private int stockItems;
    private List<String> images;

    // Getters and Setters
}
