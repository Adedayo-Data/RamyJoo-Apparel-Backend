package com.ramyjoo.fashionstore.service;

import com.ramyjoo.fashionstore.dto.SubCategoryDTO;
import com.ramyjoo.fashionstore.model.Category;
import com.ramyjoo.fashionstore.model.SubCategory;
import com.ramyjoo.fashionstore.repository.CategoryRepository;
import com.ramyjoo.fashionstore.repository.SubCategoryRepository;
import com.ramyjoo.fashionstore.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCatRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    public SubCategory setSubCategory(String mainCategory, String subCategory) {
        SubCategory category = subCatRepo.findBySubCategoryName(ValidationUtils.getInstance().trimAndLowerCase(subCategory));
        System.out.println("SubCategory: " + category);
        // TO FIX: Exception handling
        return Objects.requireNonNullElseGet(category, () -> createSubCategory(mainCategory, subCategory));
    }

    public SubCategory createSubCategory(String mainCategory, String subCategory) {
        Category cat = new Category();
        SubCategory subCat = new SubCategory();

        cat.setCategoryName(mainCategory);
        Category savedCat = categoryRepo.save(cat);

        subCat.setSubCategoryName(subCategory);
        subCat.setParentCategory(savedCat);

        return subCatRepo.save(subCat);
    }

    public List<Category> allCategory() {
        return categoryRepo.findAll();
    }

    public List<SubCategory> allSubCategory() {
        return subCatRepo.findAll();
    }

    public Category updateCategory(Long id, String name) {
        Optional<Category> category = categoryRepo.findById(id);

        Category updatedCategory = category.orElse(null);

        if (updatedCategory == null) {
            throw new RuntimeException("An error occured!");
        }

        updatedCategory.setCategoryName(name);

        return categoryRepo.save(updatedCategory);
    }

    public SubCategory updateSubCategory(Long id, String category, String updatedSubCat) {
//        SubCategoryDTO subCategoryDTO
        Optional<SubCategory> subCategory = subCatRepo.findById(id);

        SubCategory updatedSubCategory = subCategory.orElse(null);

        if (updatedSubCategory == null) {
            throw new RuntimeException("An error occured!");
        }
        Category demoCategory = updatedSubCategory.getParentCategory();

        if(category!=null){
            demoCategory.setCategoryName(category);
        }
        updatedSubCategory.setSubCategoryName(updatedSubCat);

        return subCatRepo.save(updatedSubCategory);
    }

    public void deleteCategory(Long id) {

        categoryRepo.deleteById(id);
    }

    public void deleteSubCategory(Long id) {

        subCatRepo.deleteById(id);
    }

}
