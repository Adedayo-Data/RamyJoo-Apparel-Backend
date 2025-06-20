package com.ramyjoo.fashionstore.service;

import com.ramyjoo.fashionstore.model.Product;
import com.ramyjoo.fashionstore.dto.CreateProductRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface ProductService {

    List<Product> getAllProducts(); //home page/shop page

    Optional<Product> getProductById(Long id) throws Exception;

    List<Product> filterByCategory(String  categoryName);

    List<Product> filterBySubCategory(String subCategory);

    List<Product> filterByPrice(BigDecimal price);

    List<Product> filterBySize(String size);

    List<Product> filterByColor(String color);

    List<Product> searchProduct(String Keyword);

    // Admin

    Product createProduct(CreateProductRequest createProductRequest);

    Product updateProduct(Long id, CreateProductRequest updateRequest);

    Product UpdateProductStatus(Long id) throws Exception;

    void deleteProduct(Long id) throws Exception;




}
