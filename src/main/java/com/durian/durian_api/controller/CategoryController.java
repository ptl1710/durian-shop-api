// CategoryController.java (trong package com.durian.durian_api.controller)
package com.durian.durian_api.controller;

import com.durian.durian_api.dto.ApiResponse;
import com.durian.durian_api.dto.Category.CategoryDTO;
import com.durian.durian_api.dto.Category.CreateCategoryDTO;
import com.durian.durian_api.dto.Category.UpdateCategoryDTO;

import com.durian.durian_api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Lấy tất cả danh mục
    @GetMapping
    public Object getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        ApiResponse<List<CategoryDTO>> res = new ApiResponse<List<CategoryDTO>>();
        res.setMessage("Lấy danh sách danh mục thành công");
        res.setData(categories);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // Lấy danh mục theo ID
    @GetMapping("/{id}")
    public Object getCategoryById(@PathVariable Long id) {
        CategoryDTO categories = categoryService.getCategoryById(id);
        ApiResponse<CategoryDTO> res = new ApiResponse<CategoryDTO>();
        res.setMessage("Lấy thông tin danh mục thành công");
        res.setData(categories);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // Tạo mới danh mục
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Object createCategory(@Valid @RequestBody CreateCategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);
        ApiResponse<CategoryDTO> res = new ApiResponse<CategoryDTO>();
        res.setMessage("Tạo danh mục thành công");
        res.setData(savedCategory);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // Cập nhật danh mục
    @PutMapping("/{id}")
    public ApiResponse<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryDTO categoryDTO
    ) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return new ApiResponse<>("Cập nhật danh mục thành công", updatedCategory);
    }

    // Xóa danh mục
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ApiResponse<>("Xóa danh mục thành công", null);
    }
}