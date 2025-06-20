package com.ramyjoo.fashionstore.repository;

import com.ramyjoo.fashionstore.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    SubCategory findBySubCategoryName(String value);
}
