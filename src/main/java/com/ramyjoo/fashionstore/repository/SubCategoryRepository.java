package com.ramyjoo.fashionstore.repository;

import com.ramyjoo.fashionstore.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    SubCategory findBySubCategoryName(String value);

    @Query("SELECT DISTINCT s.subCategoryName FROM SubCategory s WHERE s IS NOT NULL")
    List<String> getAllSubCategoryName();

}
