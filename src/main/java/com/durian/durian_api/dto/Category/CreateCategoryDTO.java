package com.durian.durian_api.dto.Category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryDTO {
    @NotBlank(message = "Tên danh mục không được trống")
    private String name;
}
