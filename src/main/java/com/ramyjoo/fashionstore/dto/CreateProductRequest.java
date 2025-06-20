package com.ramyjoo.fashionstore.dto;

import com.ramyjoo.fashionstore.model.SubCategory;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateProductRequest {

    private String productName;
    private String description;
    private BigDecimal price;
    private String brand;
    private String Category; // set this to subcategory
    private String subCategory;
    private List<String> sizeList = new ArrayList<>();
    private List<String> colorList = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private Boolean available;

}
