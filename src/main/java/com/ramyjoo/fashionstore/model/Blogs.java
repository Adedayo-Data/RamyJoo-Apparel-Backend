package com.ramyjoo.fashionstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Blogs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
    private String author;
    private String featuredImage;
    private LocalDate createdAt;
}
