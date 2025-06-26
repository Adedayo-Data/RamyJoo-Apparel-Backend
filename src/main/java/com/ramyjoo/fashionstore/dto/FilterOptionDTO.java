package com.ramyjoo.fashionstore.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilterOptionDTO {

    private List<String> brands;
    private List<String> colors;
    private List<String> categories;
}
