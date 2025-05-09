package com.durian.durian_api.dto.Category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCategoryDTO {
    @NotBlank(message = "Tên danh mục không được trống")
    public String name;
}
