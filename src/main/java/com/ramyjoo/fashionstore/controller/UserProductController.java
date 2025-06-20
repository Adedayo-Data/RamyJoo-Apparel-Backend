package com.ramyjoo.fashionstore.controller;

import com.ramyjoo.fashionstore.dto.ProductResponseDTO;
import com.ramyjoo.fashionstore.model.Product;
import com.ramyjoo.fashionstore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products/")
public class UserProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> allProduct(){
        List<ProductResponseDTO> productResponseList = new ArrayList<>();

        List<Product> allProduct = productService.getAllProducts();

       
        for(Product p : allProduct){
            ProductResponseDTO responseDTO =  modelMapper.map(Product.class, ProductResponseDTO.class);
            productResponseList.add(responseDTO);
        }

        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    // VIEW SINGLE PRODUCT -> detailed view
    // VIEW ALL PRODUCT -> make summary DTO v2
    @GetMapping("/{id}")
    public ResponseEntity<List<ProductResponseDTO>> getProductById(@PathVariable Long id) throws Exception{
        List<ProductResponseDTO> productResponseList = new ArrayList<>();

        List<Product> allProduct = productService.getAllProducts();

//        modelMapper.createTypeMap(Product.class, ProductResponseDTO.class)
//                .addMapping(prod -> prod.getSubCategory().getParentCategory().getCategoryName(), ProductResponseDTO::setCategory)
//                .addMapping(prod-> prod.getSubCategory().getSubCategoryName(), ProductResponseDTO::setSubCategory);

        for(Product p : allProduct){
            ProductResponseDTO responseDTO =  modelMapper.map(p, ProductResponseDTO.class);
            productResponseList.add(responseDTO);
        }

        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    @GetMapping("/by-category")// TO FIX: should be category id
    public ResponseEntity<List<ProductResponseDTO>> filterByCategory(@RequestParam String categoryName) throws Exception{
        List<ProductResponseDTO> productResponseList = new ArrayList<>();

        List<Product> productByCategory = productService.filterByCategory(categoryName);
        
        for(Product p : productByCategory){
            ProductResponseDTO responseDTO =  modelMapper.map(p, ProductResponseDTO.class);
            productResponseList.add(responseDTO);
        }

        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    @GetMapping("/by-subcategory") // TO FIX: should be category id
    public ResponseEntity<List<ProductResponseDTO>> filterBySubCategory(@RequestParam String subCategoryName) throws Exception{
        List<ProductResponseDTO> productResponseList = new ArrayList<>();

        List<Product> productBySubCategory = productService.filterByCategory(subCategoryName);
        
        for(Product p : productBySubCategory){
            ProductResponseDTO responseDTO =  modelMapper.map(p, ProductResponseDTO.class);
            productResponseList.add(responseDTO);
        }

        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    @GetMapping("/by-price")// TO FIX: should be category id
    public ResponseEntity<List<ProductResponseDTO>> filterByPrice(@RequestParam BigDecimal price) throws Exception{
        List<ProductResponseDTO> productResponseList = new ArrayList<>();

        List<Product> productByPrice = productService.filterByPrice(price);

       
        for(Product p : productByPrice){
            ProductResponseDTO responseDTO =  modelMapper.map(Product.class, ProductResponseDTO.class);
            productResponseList.add(responseDTO);
        }

        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    @GetMapping("/by-size") // TO FIX: should be category id
    public ResponseEntity<List<ProductResponseDTO>> filterBySize(@RequestParam String size) throws Exception{
        List<ProductResponseDTO> productResponseList = new ArrayList<>();

        List<Product> productBySize = productService.filterBySize(size);

       
        for(Product p : productBySize){
            ProductResponseDTO responseDTO =  modelMapper.map(p, ProductResponseDTO.class);
            productResponseList.add(responseDTO);
        }

        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    @GetMapping("/by-color") // TO FIX: should be category id
    public ResponseEntity<List<ProductResponseDTO>> filterByColor(@RequestParam String color) throws Exception{
        List<ProductResponseDTO> productResponseList = new ArrayList<>();

        List<Product> productByColor = productService.filterByColor(color);

       
        for(Product p : productByColor){
            ProductResponseDTO responseDTO =  modelMapper.map(p, ProductResponseDTO.class);
            productResponseList.add(responseDTO);
        }

        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }

    @GetMapping ("search")// TO FIX: should be category id
    public ResponseEntity<List<ProductResponseDTO>> searchProduct(@RequestParam String keyword) throws Exception{
        List<ProductResponseDTO> productResponseList = new ArrayList<>();

        List<Product> productByColor = productService.searchProduct(keyword);

       
        for(Product p : productByColor){
            ProductResponseDTO responseDTO =  modelMapper.map(p, ProductResponseDTO.class);
            productResponseList.add(responseDTO);
        }

        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }
}
