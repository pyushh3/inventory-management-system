package com.clothing.inventory.product.service;

import com.clothing.inventory.product.dto.ProductRequestDto;
import com.clothing.inventory.product.dto.ProductResponseDto;
import com.clothing.inventory.product.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    public ProductResponseDto createProduct(ProductRequestDto requestDto, MultipartFile image);

    public Page<ProductResponseDto> getAllProducts(
            int page,
            int size,
            String sortBy,
            String direction,
            String search,
            Long categoryId,
            Long brandId,
            Gender gender
    );

    public ProductResponseDto getProductById(Long id);

    ProductResponseDto updateProduct(
            Long id,
            ProductRequestDto requestDto,
            MultipartFile image);

    public void softDelete(Long id);
}
