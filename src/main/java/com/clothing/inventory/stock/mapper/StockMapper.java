package com.clothing.inventory.stock.mapper;

import com.clothing.inventory.product.entity.Product;
import com.clothing.inventory.stock.dto.LowStockResponseDto;
import com.clothing.inventory.stock.dto.StockRequestDto;
import com.clothing.inventory.stock.dto.StockResponseDto;
import com.clothing.inventory.stock.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    public Stock toEntity(StockRequestDto dto) {

        Stock movement = new Stock();

        movement.setQuantity(dto.getQuantity());
        movement.setReason(dto.getReason());

        return movement;
    }

    public StockResponseDto toResponse(
            Stock movement,
            Integer currentQuantity) {

        StockResponseDto response =
                new StockResponseDto();

        response.setId(movement.getId());

        response.setProductId(movement.getProduct().getId());
        response.setProductName(movement.getProduct().getName());
        response.setSku(movement.getProduct().getSku());

        response.setType(movement.getType());
        response.setQuantity(movement.getQuantity());
        response.setReason(movement.getReason());
        response.setCreatedAt(movement.getCreatedAt());

        return response;
    }

    public LowStockResponseDto toLowStockResponse(Product product) {

        LowStockResponseDto response = new LowStockResponseDto();

        response.setProductId(product.getId());
        response.setProductName(product.getName());
        response.setSku(product.getSku());
        response.setBrandName(product.getBrand().getName());
        response.setCategoryName(product.getCategory().getName());
        response.setQuantity(product.getQuantity());

        return response;
    }
}
