package com.ramyjoo.fashionstore.service;

import com.ramyjoo.fashionstore.dto.ProductResponseDTO;
import com.ramyjoo.fashionstore.dto.FilterOptionDTO;
import com.ramyjoo.fashionstore.exceptions.ResourceNotFoundException;
import com.ramyjoo.fashionstore.model.Product;
import com.ramyjoo.fashionstore.dto.CreateProductRequest;
import com.ramyjoo.fashionstore.repository.ProductRepository;
import com.ramyjoo.fashionstore.repository.SubCategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepo;
    private final SubCategoryRepository subCategoryRepo;
    private final SubCategoryService subCategoryService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Override
    public Page<Product> getAllProducts(int page, int size,
                                        String category, String color, String brand,
                                        String keyword, BigDecimal minPrice, BigDecimal maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Product> spec = ProductSpecification.filterBy(category, color, brand, keyword, minPrice, maxPrice);
        return productRepo.findAll(spec, pageable);
    }

    public List<Product> homepageProducts(){
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

        System.out.println(product);
        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(Long id, CreateProductRequest updateRequest) {
        Optional<Product> product = productRepo.findById(id);

        System.out.println(product);

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

    @Override
    public ProductResponseDTO updateProductImage(Long productId, MultipartFile file) throws IOException {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        System.out.println("Product is: " +product);
        String imageUrl = cloudinaryService.uploadImage(file);

        List<String> images = product.getImages();
        images.add(imageUrl);
        product.setImages(images);

        Product savedProduct = productRepo.save(product);
        return modelMapper.map(savedProduct, ProductResponseDTO.class);
    }

    @Override
    public List<ProductResponseDTO> filterProducts(
            String categoryName,
            String subCategoryName,
            BigDecimal price,
            String size,
            String color,
            String keyword
    ) {
        List<Product> products = productRepo.findAll();

//        if (categoryName != null && !categoryName.isBlank()) {
//            products = products.stream()
//                    .filter(p -> p.getSubCategory() != null &&
//                            p.getSubCategory().getParentCategory() != null &&
//                            p.getSubCategory().getParentCategory().getCategoryName().equalsIgnoreCase(categoryName))
//                    .collect(Collectors.toList());
//        }

        if (subCategoryName != null && !subCategoryName.isBlank()) {
            products = products.stream()
                    .filter(p -> p.getSubCategory() != null &&
                            p.getSubCategory().getSubCategoryName().equalsIgnoreCase(subCategoryName))
                    .collect(Collectors.toList());
        }

        if (price != null) {
            products = products.stream()
                    .filter(p -> p.getPrice() != null && p.getPrice().compareTo(price) == 0)
                    .collect(Collectors.toList());
        }

        if (size != null && !size.isBlank()) {
            products = products.stream()
                    .filter(p -> p.getSizeList() != null && p.getSizeList().contains(size))
                    .collect(Collectors.toList());
        }

        if (color != null && !color.isBlank()) {
            products = products.stream()
                    .filter(p -> p.getColorList() != null && p.getColorList().contains(color))
                    .collect(Collectors.toList());
        }

        if (keyword != null && !keyword.isBlank()) {
            String lowerKeyword = keyword.toLowerCase();
            products = products.stream()
                    .filter(p -> (p.getProductName() != null && p.getProductName().toLowerCase().contains(lowerKeyword)) ||
                            (p.getBrand() != null && p.getBrand().toLowerCase().contains(lowerKeyword)) ||
                            (p.getColorList() != null &&
                                    p.getColorList().stream().anyMatch(c -> c.toLowerCase().contains(lowerKeyword))))
                    .collect(Collectors.toList());
        }

        // Map to DTOs
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
    }

    public FilterOptionDTO filterOptions(){
        FilterOptionDTO filterOptionDTO = new FilterOptionDTO();
        filterOptionDTO.setBrands(productRepo.getAllBrands());
        filterOptionDTO.setColors(productRepo.getAllColorList());
        filterOptionDTO.setCategories(subCategoryRepo.getAllSubCategoryName());

        return filterOptionDTO;
    }

}

