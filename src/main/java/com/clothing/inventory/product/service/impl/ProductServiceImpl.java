package com.clothing.inventory.product.service.impl;

import com.clothing.inventory.brand.entity.Brand;
import com.clothing.inventory.brand.repository.BrandRepo;
import com.clothing.inventory.category.entity.Category;
import com.clothing.inventory.category.repository.CategoryRepo;
import com.clothing.inventory.exception.DuplicateResourceException;
import com.clothing.inventory.exception.ResourceNotFoundException;
import com.clothing.inventory.product.dto.ProductRequestDto;
import com.clothing.inventory.product.dto.ProductResponseDto;
import com.clothing.inventory.product.entity.Product;
import com.clothing.inventory.product.enums.Gender;
import com.clothing.inventory.product.mapper.ProductMapper;
import com.clothing.inventory.product.repository.ProductRepository;
import com.clothing.inventory.product.service.ProductService;
import com.clothing.inventory.product.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository pr;
    private final ProductMapper pm;
    private final CategoryRepo cr;
    private final BrandRepo br;


    public ProductServiceImpl(ProductRepository pr, ProductMapper pm, CategoryRepo cr, BrandRepo br) {
        this.pr = pr;
        this.pm = pm;
        this.cr = cr;
        this.br = br;
    }

    public ProductResponseDto createProduct(ProductRequestDto requestDto, MultipartFile image) {

        if (pr.existsBySkuIgnoreCase(requestDto.getSku())) {
            throw new DuplicateResourceException("SKU already exists.");
        }

        Category category = cr.findByIdAndDeletedFalse(requestDto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        Brand brand = br.findByIdAndDeletedFalse(requestDto.getBrandId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Brand not found."));

        Product product = pm.toEntity(requestDto);

        product.setCategory(category);
        product.setBrand(brand);
        product.setImageUrl(saveImage(image));
        pr.save(product);
        return pm.toResponse(product);
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = pr.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new ResourceNotFoundException("Product with id " + id + " not found"));

        return pm.toResponse(product);
    }

    public Page<ProductResponseDto> getAllProducts(int page, int size, String sortBy, String direction, String search, Long categoryId, Long brandId, Gender gender) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Product> spec =
                ProductSpecification.notDeleted()
                        .and(ProductSpecification.hasCategory(categoryId))
                        .and(ProductSpecification.hasBrand(brandId))
                        .and(ProductSpecification.hasGender(gender))
                        .and(ProductSpecification.search(search));

        Page<Product> products = pr.findAll(spec, pageable);

        return products.map(pm::toResponse);
    }

    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto, MultipartFile image) {
        Product product = pr.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        // Check duplicate SKU
        if (!product.getSku().equalsIgnoreCase(requestDto.getSku())
                && pr.existsBySkuIgnoreCase(requestDto.getSku())) {

            throw new DuplicateResourceException("SKU already exists.");
        }

        Category category = cr.findByIdAndDeletedFalse(requestDto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        Brand brand = br.findByIdAndDeletedFalse(requestDto.getBrandId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Brand not found."));

        // Update fields
        pm.updateEntity(requestDto, product);

        product.setCategory(category);
        product.setBrand(brand);

        // Update image only if a new image is uploaded
        if (image != null && !image.isEmpty()) {
            product.setImageUrl(saveImage(image));
        }

        pr.save(product);

        return pm.toResponse(product);
    }

    public void softDelete(Long id) {
        Product product = pr.findByIdAndDeletedFalse(id).orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        product.setDeleted(true);
        pr.save(product);

    }

    // helper methods
    private String saveImage(MultipartFile image) {
        try {
            validateImage(image);

            Path uploadPath = Path.of(System.getProperty("user.dir"), "uploads", "products");

            Files.createDirectories(uploadPath);

            String fileName = System.currentTimeMillis() + "_"
                    + image.getOriginalFilename();

            Path filePath = uploadPath.resolve(fileName);

            image.transferTo(filePath.toFile());

            return filePath.toString().replace("\\", "/");

        } catch (IOException e) {
            throw new RuntimeException("Failed to save image.", e);
        }
    }

    private void validateImage(MultipartFile image) {

        if (image == null || image.isEmpty()) {
            throw new RuntimeException("Image is required.");
        }

        List<String> allowedTypes = List.of(
                "image/jpeg",
                "image/png",
                "image/webp"
        );

        if (!allowedTypes.contains(image.getContentType())) {
            throw new RuntimeException(
                    "Only JPG, PNG and WEBP images are allowed."
            );
        }

        if (image.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("Image size must not exceed 5 MB.");
        }
    }
}
