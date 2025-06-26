package com.ramyjoo.fashionstore.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {

    private String productName;
    private String description;
    private BigDecimal price;
    private String brand;
    private String Category; // set this to subcategory TO FIX: Fix in model Mapper
    private String subCategory;
    private List<String> sizeList;
    private List<String> colorList;
    private List<String> images;
    private Boolean available;
}
