package com.clothing.inventory.product.controller;

import com.clothing.inventory.product.dto.ProductRequestDto;
import com.clothing.inventory.product.dto.ProductResponseDto;
import com.clothing.inventory.product.enums.Gender;
import com.clothing.inventory.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService ps;

    public ProductController(ProductService ps) {
        this.ps = ps;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponseDto createProduct(@Valid @RequestPart("product") ProductRequestDto requestDto, @RequestPart("image") MultipartFile image) {

        return ps.createProduct(requestDto, image);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {

        return ResponseEntity.ok(ps.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "3") int size,
                                                                   @RequestParam(defaultValue = "createdAt") String sortBy,
                                                                   @RequestParam(defaultValue = "asc") String direction,
                                                                   @RequestParam(required = false) String search,
                                                                   @RequestParam(required = false) Long categoryId,
                                                                   @RequestParam(required = false) Long brandId,
                                                                   @RequestParam(required = false) Gender gender
                                                                   ) {
        return ResponseEntity.ok(
                ps.getAllProducts(page, size, sortBy,
                        direction, search, categoryId, brandId, gender));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestPart("product") @Valid ProductRequestDto requestDto, @RequestPart(value = "image", required = false)
    MultipartFile image) {

        return ResponseEntity.ok(
                ps.updateProduct(id, requestDto, image));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {

        ps.softDelete(id);

        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
