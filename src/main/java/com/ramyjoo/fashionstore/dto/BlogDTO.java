package com.ramyjoo.fashionstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlogDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String author;
    private String featuredImage;
}
