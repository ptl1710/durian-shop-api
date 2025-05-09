// CategoryService.java (trong package com.durian.durian_api.service)
package com.durian.durian_api.service;

import com.durian.durian_api.dto.Category.CategoryDTO;
import com.durian.durian_api.dto.Category.CreateCategoryDTO;
import com.durian.durian_api.dto.Category.UpdateCategoryDTO;
import com.durian.durian_api.exception.ResourceNotFoundException;
import com.durian.durian_api.model.Category;
import com.durian.durian_api.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    // Lấy tất cả danh mục
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lấy danh mục theo ID
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));
        return convertToDTO(category);
    }

    // Tạo mới danh mục
    public CategoryDTO createCategory(CreateCategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    // Cập nhật danh mục
    public CategoryDTO updateCategory(Long id, UpdateCategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));
        modelMapper.map(categoryDTO, category);
        return convertToDTO(categoryRepository.save(category));
    }

    // Xóa danh mục
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));
        categoryRepository.delete(category);
    }

    // Convert Entity → DTO
    private CategoryDTO convertToDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }
}