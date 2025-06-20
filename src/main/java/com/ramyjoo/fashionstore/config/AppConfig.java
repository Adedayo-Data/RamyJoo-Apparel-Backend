package com.ramyjoo.fashionstore.config;

import com.ramyjoo.fashionstore.dto.ProductResponseDTO;
import com.ramyjoo.fashionstore.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Product.class, ProductResponseDTO.class)
                .addMapping(prod -> prod.getSubCategory().getParentCategory().getCategoryName(), ProductResponseDTO::setCategory)
                .addMapping(prod-> prod.getSubCategory().getSubCategoryName(), ProductResponseDTO::setSubCategory);

        return modelMapper;
    }
}
