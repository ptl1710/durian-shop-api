package com.durian.durian_api.dto.Product;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;

public class UpdateProductDTO {
    @Getter
    @NotBlank(message = "Tên sản phẩm không được trống")
    private String name;

    @Getter
    @Size(max = 500, message = "Mô tả tối đa 500 ký tự")
    private String description;

    @Getter
    @Positive(message = "Giá phải lớn hơn 0")
    private double price;

    @Getter
    @PositiveOrZero(message = "Số lượng tồn kho không được âm")
    private int stock;

    @Getter
    private String imageUrl;

    @Getter
    private float rating;

    @Getter
    @NotNull(message = "Danh mục là bắt buộc")
    private Long categoryId;

}
