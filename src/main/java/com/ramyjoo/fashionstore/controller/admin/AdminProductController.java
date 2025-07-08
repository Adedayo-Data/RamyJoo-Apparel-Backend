package com.ramyjoo.fashionstore.controller.admin;

import com.ramyjoo.fashionstore.dto.CreateProductRequest;
import com.ramyjoo.fashionstore.dto.ProductResponseDTO;
import com.ramyjoo.fashionstore.model.Product;
import com.ramyjoo.fashionstore.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/admin/products")
@AllArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @PostMapping("/createProduct")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody CreateProductRequest request){
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        Product product = productService.createProduct(request);

        System.out.println(product.toString());

        responseDTO =  modelMapper.map(product, ProductResponseDTO.class);
//        responseDTO.setProductName(request.getProductName());
//        responseDTO.setDescription(request.getDescription());
//        responseDTO.setPrice(request.getPrice());
//        responseDTO.setBrand(request.getBrand());
//        responseDTO.setCategory(product.getSubCategory().getParentCategory().getCategoryName());
//        responseDTO.setSubCategory(product.getSubCategory().getSubCategoryName());
//        responseDTO.setColorList(request.getColorList());
//        responseDTO.setSizeList(request.getSizeList());
//        responseDTO.setImages(request.getImages());
//        responseDTO.setAvailable(request.getAvailable());

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestBody CreateProductRequest updateRequest,
                                                            @PathVariable Long id){
        Product product = productService.updateProduct(id, updateRequest);
        new ProductResponseDTO();
        ProductResponseDTO responseDTO;

        responseDTO =  modelMapper.map(product, ProductResponseDTO.class);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws Exception{

        productService.deleteProduct(id);

        return new ResponseEntity<>("Operation Successful", HttpStatus.OK);
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<String> updateProductStatus(@PathVariable Long id) throws Exception {
        try {
            Product product = productService.UpdateProductStatus(id);

            return new ResponseEntity<>("Product Status updated", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred", HttpStatus.NOT_MODIFIED);
        }
    }
    //OTHER METHODS - WOULD CONSIDER
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
//
//    // VIEW SINGLE PRODUCT -> detailed view
//    // VIEW ALL PRODUCT -> make summary DTO v2
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> productById(@PathVariable Long id) throws Exception{
//        List<ProductResponseDTO productResponseList = new ArrayList<>();

        Optional<Product> allProduct = productService.getProductById(id);

        Product product  = allProduct.orElseThrow(() ->
                new RuntimeException("Product not Found!"));

        ProductResponseDTO responseDTO =  modelMapper.map(product, ProductResponseDTO.class);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<ProductResponseDTO> updateProductImage(
            @PathVariable Long id,
            @RequestParam MultipartFile file) throws IOException {

        return ResponseEntity.ok(productService.updateProductImage(id, file));
    }

    //
//    @GetMapping// TO FIX: sh@RequestParam MultipartFile fileould be category id
//    public ResponseEntity<List<ProductResponseDTO>> filterByCategory(@RequestParam String categoryName) throws Exception{
//        List<ProductResponseDTO> productResponseList = new ArrayList<>();
//
//        List<Product> productByCategory = productService.filterByCategory(categoryName);
//
//        modelMapper.createTypeMap(Product.class, ProductResponseDTO.class)
//                .addMapping(prod -> prod.getSubCategory().getParentCategory().getCategoryName(), ProductResponseDTO::setCategory)
//                .addMapping(prod-> prod.getSubCategory().getSubCategoryName(), ProductResponseDTO::setSubCategory);
//
//        for(Product p : productByCategory){
//            ProductResponseDTO responseDTO =  modelMapper.map(Product.class, ProductResponseDTO.class);
//            productResponseList.add(responseDTO);
//        }
//
//        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
//    }
//
//    @GetMapping // TO FIX: should be category id
//    public ResponseEntity<List<ProductResponseDTO>> filterBySubCategory(@RequestParam String subCategoryName) throws Exception{
//        List<ProductResponseDTO> productResponseList = new ArrayList<>();
//
//        List<Product> productBySubCategory = productService.filterByCategory(subCategoryName);
//
//        modelMapper.createTypeMap(Product.class, ProductResponseDTO.class)
//                .addMapping(prod -> prod.getSubCategory().getParentCategory().getCategoryName(), ProductResponseDTO::setCategory)
//                .addMapping(prod-> prod.getSubCategory().getSubCategoryName(), ProductResponseDTO::setSubCategory);
//
//        for(Product p : productBySubCategory){
//            ProductResponseDTO responseDTO =  modelMapper.map(Product.class, ProductResponseDTO.class);
//            productResponseList.add(responseDTO);
//        }
//
//        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
//    }
//
//    @GetMapping// TO FIX: should be category id
//    public ResponseEntity<List<ProductResponseDTO>> filterByPrice(@RequestParam BigDecimal price) throws Exception{
//        List<ProductResponseDTO> productResponseList = new ArrayList<>();
//
//        List<Product> productByPrice = productService.filterByPrice(price);
//
//        modelMapper.createTypeMap(Product.class, ProductResponseDTO.class)
//                .addMapping(prod -> prod.getSubCategory().getParentCategory().getCategoryName(), ProductResponseDTO::setCategory)
//                .addMapping(prod-> prod.getSubCategory().getSubCategoryName(), ProductResponseDTO::setSubCategory);
//
//        for(Product p : productByPrice){
//            ProductResponseDTO responseDTO =  modelMapper.map(Product.class, ProductResponseDTO.class);
//            productResponseList.add(responseDTO);
//        }
//
//        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
//    }
//
//    @GetMapping // TO FIX: should be category id
//    public ResponseEntity<List<ProductResponseDTO>> filterBySize(@RequestParam String size) throws Exception{
//        List<ProductResponseDTO> productResponseList = new ArrayList<>();
//
//        List<Product> productBySize = productService.filterBySize(size);
//
//        modelMapper.createTypeMap(Product.class, ProductResponseDTO.class)
//                .addMapping(prod -> prod.getSubCategory().getParentCategory().getCategoryName(), ProductResponseDTO::setCategory)
//                .addMapping(prod-> prod.getSubCategory().getSubCategoryName(), ProductResponseDTO::setSubCategory);
//
//        for(Product p : productBySize){
//            ProductResponseDTO responseDTO =  modelMapper.map(Product.class, ProductResponseDTO.class);
//            productResponseList.add(responseDTO);
//        }
//
//        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
//    }
//
//    @GetMapping // TO FIX: should be category id
//    public ResponseEntity<List<ProductResponseDTO>> filterByColor(@RequestParam String color) throws Exception{
//        List<ProductResponseDTO> productResponseList = new ArrayList<>();
//
//        List<Product> productByColor = productService.filterByColor(color);
//
//        modelMapper.createTypeMap(Product.class, ProductResponseDTO.class)
//                .addMapping(prod -> prod.getSubCategory().getParentCategory().getCategoryName(), ProductResponseDTO::setCategory)
//                .addMapping(prod-> prod.getSubCategory().getSubCategoryName(), ProductResponseDTO::setSubCategory);
//
//        for(Product p : productByColor){
//            ProductResponseDTO responseDTO =  modelMapper.map(Product.class, ProductResponseDTO.class);
//            productResponseList.add(responseDTO);
//        }
//
//        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
//    }
//
    @GetMapping("/search") // TO FIX: should be category id
    public ResponseEntity<List<ProductResponseDTO>> searchProduct(@RequestParam String keyword) throws Exception{
        List<ProductResponseDTO> productResponseList = new ArrayList<>();

        List<Product> searchProduct = productService.searchProduct(keyword);
        System.out.println(searchProduct);

        for(Product p : searchProduct){
            ProductResponseDTO responseDTO =  modelMapper.map(p, ProductResponseDTO.class);
            productResponseList.add(responseDTO);
        }

        return new ResponseEntity<>(productResponseList, HttpStatus.OK);
    }
}
