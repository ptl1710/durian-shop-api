package com.durian.durian_api.dto.Category;

import com.durian.durian_api.model.Product;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class CategoryDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

}
