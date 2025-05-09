package com.durian.durian_api.controller;

import com.durian.durian_api.dto.ApiResponse;
import com.durian.durian_api.dto.PagedResponse;
import com.durian.durian_api.dto.Product.CreateProductDTO;
import com.durian.durian_api.dto.Product.ProductDTO;
import com.durian.durian_api.dto.Product.UpdateProductDTO;
import com.durian.durian_api.model.Product;
import com.durian.durian_api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<ProductDTO>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,  // Trang mặc định: 0
            @RequestParam(defaultValue = "10") int size, // Số lượng phần tử mỗi trang mặc định: 10
            @RequestParam(defaultValue = "id") String sort // Sắp xếp mặc định theo trường "id"
    ) {
        // Tạo đối tượng phân trang và sắp xếp
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        PagedResponse<ProductDTO> pagedResponse = productService.getAllProducts(pageable);

        ApiResponse<PagedResponse<ProductDTO>> response = new ApiResponse<>();
        response.setMessage("Lấy danh sách sản phẩm thành công");
        response.setData(pagedResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Object getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        ApiResponse<ProductDTO> response = new ApiResponse<ProductDTO>();
        response.setMessage("Lấy sản phẩm thành công");
        response.setData(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Create product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Object createProduct(@Valid @RequestBody CreateProductDTO productCreateDTO) {
        Product product = productService.createProduct(productCreateDTO);
        ProductDTO productDTO = productService.convertToDTO(product);
        return new ApiResponse<>("Tạo sản phẩm thành công", productDTO);
    }

    // Update product
    @PutMapping("/{id}")
    public Object updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductDTO productUpdateDTO
    ) {
        Product product = productService.updateProduct(id, productUpdateDTO);
        ProductDTO productDTO = productService.convertToDTO(product);
        return new ApiResponse<>("Cập nhật sản phẩm thành công", productDTO);
    }

    // Delete product
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ApiResponse<>("Xóa sản phẩm thành công", null);
    }
}
