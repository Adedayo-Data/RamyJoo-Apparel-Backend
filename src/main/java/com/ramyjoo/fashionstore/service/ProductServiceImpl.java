package com.ramyjoo.fashionstore.service;

import com.ramyjoo.fashionstore.model.Product;
import com.ramyjoo.fashionstore.dto.CreateProductRequest;
import com.ramyjoo.fashionstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private SubCategoryService subCategoryService;

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) throws Exception {
        return productRepo.findById(id);
    }

    @Override
    public List<Product> filterByCategory(String  categoryName) {
        return productRepo.filterByCategory(categoryName);
    }

    @Override
    public List<Product> filterBySubCategory(String subCategory) {
        return productRepo.filterBySubCategory(subCategory);
    }

    @Override
    public List<Product> filterByPrice(BigDecimal price) {
        return productRepo.filterByPrice(price);
    }

    @Override
    public List<Product> filterBySize(String size) {
        return productRepo.filterBySize(size);
    }

    @Override
    public List<Product> filterByColor(String color) {
        return productRepo.filterByColor(color);
    }

    @Override
    public List<Product> searchProduct(String keyword) {
        return productRepo.searchProduct(keyword);
    }

    @Override
    public Product createProduct(CreateProductRequest request) {
        // TO FIX: Exception Handling
        Product product = new Product();

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setBrand(request.getBrand());
        product.setSubCategory(subCategoryService.setSubCategory(request.getCategory(), request.getSubCategory()));
        product.setColorList(request.getColorList());
        product.setSizeList(request.getSizeList());
        product.setImages(request.getImages());
        product.setAvailable(request.getAvailable());

        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(Long id, CreateProductRequest updateRequest) {
        Optional<Product> product = productRepo.findById(id);

        System.out.println(product.toString());

        product.ifPresent(realProduct -> realProduct.setProductName(updateRequest.getProductName()));

        product.ifPresent(realProduct -> realProduct.setDescription(updateRequest.getDescription()));

        product.ifPresent(realProduct -> realProduct.setPrice(updateRequest.getPrice()));

        product.ifPresent(realProduct -> realProduct.setBrand(updateRequest.getBrand()));

        product.ifPresent(realProduct -> realProduct.setSubCategory(subCategoryService.setSubCategory(updateRequest.getCategory(), updateRequest.getSubCategory())));

        product.ifPresent(realProduct -> realProduct.setSizeList(updateRequest.getSizeList()));

        product.ifPresent(realProduct -> realProduct.setColorList(updateRequest.getColorList()));

        product.ifPresent(realProduct -> realProduct.setImages(updateRequest.getImages()));

        product.ifPresent(realProduct -> realProduct.setAvailable(updateRequest.getAvailable()));

        return product.map(value -> productRepo.save(value)).orElseThrow(() -> new RuntimeException("An error occured while updating product"));
    }

    @Override
    public Product UpdateProductStatus(Long id) throws Exception {
        Optional<Product> product = productRepo.findById(id);

        product.ifPresentOrElse(realProduct -> {
            realProduct.setAvailable(!realProduct.isAvailable());
        }, () -> {
            throw new RuntimeException("Product not found. Error Occurred!");
        });

        return product.map(value -> productRepo.save(value)).orElseThrow(()-> new RuntimeException("Product not found after update!"));
    }

    @Override
    public void deleteProduct(Long id) throws Exception {
        Optional<Product> product = productRepo.findById(id);

        product.ifPresentOrElse(realProduct -> {
            productRepo.delete(realProduct);
        }, () -> {
            throw new RuntimeException("Product not found. Error Occured!");
        });
    }
}
