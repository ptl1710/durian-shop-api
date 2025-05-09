package com.durian.durian_api.dto.Product;

import com.durian.durian_api.model.Category;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private float rating;
    private String imageUrl;
    private Long categoryId;
}
