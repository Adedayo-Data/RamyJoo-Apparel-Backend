package com.ramyjoo.fashionstore.controller.user;

import com.ramyjoo.fashionstore.dto.FilterOptionDTO;
import com.ramyjoo.fashionstore.dto.ProductResponseDTO;
import com.ramyjoo.fashionstore.model.Product;
import com.ramyjoo.fashionstore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products/")
public class UserProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("all")
    public ResponseEntity<List<ProductResponseDTO>> homepageProducts(){

        List<Product> allProduct = productService.homepageProducts();
        List<ProductResponseDTO> dtoList = new ArrayList<>();
        for(Product p : allProduct){
            ProductResponseDTO responseDTO = modelMapper.map(p, ProductResponseDTO.class);
            dtoList.add(responseDTO);
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> allProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max){

        Page<Product> pagedProducts = productService.getAllProducts(page, size,
                category, color, brand, keyword, min, max);
        Page<ProductResponseDTO> dtoPage = pagedProducts.map(product ->
                modelMapper.map(product, ProductResponseDTO.class)
        );
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    // VIEW SINGLE PRODUCT -> detailed view
    // VIEW ALL PRODUCT -> make summary DTO v2
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) throws Exception{
        Optional<Product> productById = productService.getProductById(id);

        if(productById.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }

        Product product = productById.get();
        ProductResponseDTO responseDTO =  modelMapper.map(product, ProductResponseDTO.class);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
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

    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponseDTO>> filterProducts(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String subCategoryName,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String keyword
    ) {
        List<ProductResponseDTO> filtered = productService.filterProducts(categoryName, subCategoryName, price, size, color, keyword);
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/filter-option")
    public ResponseEntity<FilterOptionDTO> filterOptions(){
        FilterOptionDTO filterOptionDTO = productService.filterOptions();
        return new ResponseEntity<>(filterOptionDTO, HttpStatus.OK);
    }
}
