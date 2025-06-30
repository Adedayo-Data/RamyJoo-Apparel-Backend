package com.ramyjoo.fashionstore.repository;

import com.ramyjoo.fashionstore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findAll();

    @Override
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    Optional<Product> findById(Long id);

    @Query("SELECT c from Category c WHERE c.categoryName = :category")
    List<Product> filterByCategory(@Param("category") String category);

    @Query("SELECT s from SubCategory s WHERE s.subCategoryName = :subCategory")
    List<Product> filterBySubCategory(@Param("subCategory") String subCategory);

    @Query("SELECT p FROM Product p WHERE p.price = :price")
    List<Product> filterByPrice(@Param("price")BigDecimal price);

    @Query("SELECT p FROM Product p JOIN p.sizeList s WHERE s = :size")
    List<Product> filterBySize(@Param("size") String size);

    @Query("SELECT p FROM Product p JOIN p.colorList c WHERE c = :color")
    List<Product> filterByColor(@Param("color") String color);

    @Query("SELECT p FROM Product p JOIN p.colorList c WHERE lower(p.productName) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(c) LIKE lower(concat('%', :keyword, '%')) " +
            "OR lower(p.brand) LIKE lower(concat('%', :keyword, '%'))")
    List<Product> searchProduct(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT c from Product p JOIN p.colorList c WHERE p IS NOT NULL")
    List<String> getAllColorList();

    @Query("SELECT DISTINCT p.brand FROM Product p WHERE p IS NOT NULL")
    List<String> getAllBrands();

}
