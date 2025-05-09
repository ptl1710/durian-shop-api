package com.durian.durian_api.service;

import com.durian.durian_api.dto.PagedResponse;
import com.durian.durian_api.dto.Product.CreateProductDTO;
import com.durian.durian_api.dto.Product.ProductDTO;
import com.durian.durian_api.dto.Product.UpdateProductDTO;
import com.durian.durian_api.exception.ResourceNotFoundException;
import com.durian.durian_api.model.Category;
import com.durian.durian_api.model.Product;
import com.durian.durian_api.repository.CategoryRepository;
import com.durian.durian_api.repository.ProductRepository;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public PagedResponse<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAllByDeletedFalse(pageable);
        return new PagedResponse<>(
                productPage.getContent().stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()),
                productPage.getNumber(),      // page
                productPage.getSize(),        // size
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast(),
                productPage.isFirst()
        );
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tìm thấy"));
        return convertToDTO(product);
    }

    //Create product
    public Product createProduct(CreateProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));
        product.setCategory(category);

        return productRepository.save(product);
    }

    // Update product
    public Product updateProduct(Long id, UpdateProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        product.setRating(dto.getRating());

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));
        product.setCategory(category);

        return productRepository.save(product);
    }

    // Delete product
    public void deleteProduct(Long id) {
        Product product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại"));
        productRepository.softDelete(id); // Gọi phương thức soft delete
    }

    public ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }
}
