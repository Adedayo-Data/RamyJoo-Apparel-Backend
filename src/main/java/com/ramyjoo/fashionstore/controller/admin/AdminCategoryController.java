package com.ramyjoo.fashionstore.controller.admin;

import com.ramyjoo.fashionstore.model.Category;
import com.ramyjoo.fashionstore.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories/")
public class AdminCategoryController {

    @Autowired
    SubCategoryService service;
    //all category and subcat
    // update categories and subcat
    // delete category

    // all category
    @GetMapping
    public ResponseEntity<List<Category>> allCategory(){
        return new ResponseEntity<>(service.allCategory(), HttpStatus.OK);
    }

    @GetMapping("/subcategory")
    public ResponseEntity<List<Category>> allSubCategory(){
        return new ResponseEntity<>(service.allCategory(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws Exception{
        service.deleteCategory(id);

        return new ResponseEntity<>("Operation Successful", HttpStatus.OK);
    }

}
