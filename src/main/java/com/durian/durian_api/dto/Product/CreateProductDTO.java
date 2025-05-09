package com.durian.durian_api.dto.Product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateProductDTO {
    @NotBlank(message = "Tên sản phẩm không được trống")
    private String name;

    @Size(max = 500, message = "Mô tả tối đa 500 ký tự")
    private String description;

    @Positive(message = "Giá phải lớn hơn 0")
    private double price;

    @PositiveOrZero(message = "Số lượng tồn kho không được âm")
    private int stock;

    private String imageUrl;
    private float rating;

    @NotNull(message = "Danh mục là bắt buộc")
    private Long categoryId;

}

