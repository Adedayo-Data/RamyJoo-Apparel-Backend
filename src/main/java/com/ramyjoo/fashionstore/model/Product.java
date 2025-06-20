package com.ramyjoo.fashionstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productName;

    @Column(length = 1500)
    private String description;
    private BigDecimal price;
    private String brand;

    @ManyToOne
    private SubCategory subCategory;

    @ElementCollection
    private List<String> sizeList = new ArrayList<>();

    @ElementCollection
    private List<String> colorList = new ArrayList<>();

    @ElementCollection
    private List<String> images = new ArrayList<>();

    // by default products are available
    private boolean available = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
