package com.clothing.inventory.product.mapper;

import com.clothing.inventory.product.dto.ProductRequestDto;
import com.clothing.inventory.product.dto.ProductResponseDto;
import com.clothing.inventory.product.entity.Product;
import com.clothing.inventory.stock.enums.StockStatus;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDto dto) {

        Product product = new Product();

        product.setName(dto.getName());
        product.setSku(dto.getSku());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setSize(dto.getSize());
        product.setColor(dto.getColor());
        product.setMaterial(dto.getMaterial());
        product.setGender(dto.getGender());

        return product;
    }

    public ProductResponseDto toResponse(Product product) {

        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setSku(product.getSku());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setSize(product.getSize());
        dto.setColor(product.getColor());
        dto.setMaterial(product.getMaterial());
        dto.setGender(product.getGender());
        dto.setImageUrl(product.getImageUrl());

        dto.setCategoryId(product.getCategory().getId());
        dto.setCategoryName(product.getCategory().getName());

        dto.setBrandId(product.getBrand().getId());
        dto.setBrandName(product.getBrand().getName());

        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        dto.setStockStatus(getStockStatus(product.getQuantity()));

        return dto;
    }

    public void updateEntity(ProductRequestDto dto, Product product) {

        product.setName(dto.getName());
        product.setSku(dto.getSku());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setSize(dto.getSize());
        product.setColor(dto.getColor());
        product.setMaterial(dto.getMaterial());
        product.setGender(dto.getGender());

    }

    private StockStatus getStockStatus(Integer quantity) {

        if (quantity == 0) {
            return StockStatus.OUT_OF_STOCK;
        }

        if (quantity <= 5) {
            return StockStatus.LOW_STOCK;
        }

        return StockStatus.IN_STOCK;
    }


}
