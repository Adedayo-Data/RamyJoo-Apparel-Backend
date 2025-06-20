package com.ramyjoo.fashionstore.dto;

import com.ramyjoo.fashionstore.model.Category;
import lombok.Data;

@Data
public class SubCategoryDTO {

    private Category category;

    private String subCategoryName;

}
