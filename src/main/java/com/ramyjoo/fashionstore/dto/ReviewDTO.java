package com.ramyjoo.fashionstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@AllArgsConstructor
@Data
public class ReviewDTO {

    private String author;
    private String image;
    private String content;
    private float rating;
    private Date date;
}
