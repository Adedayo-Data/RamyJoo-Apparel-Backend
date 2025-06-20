package com.ramyjoo.fashionstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String subCategoryName;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category parentCategory;

    // IF THIS DELETES PRODUCTS TOO WE WOULD SET DEFAULT CATEGORY FOR MVP
}
