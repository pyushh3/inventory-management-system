package com.clothing.inventory.stock.service;

import com.clothing.inventory.product.dto.ProductResponseDto;
import com.clothing.inventory.product.entity.Product;
import com.clothing.inventory.stock.dto.LowStockResponseDto;
import com.clothing.inventory.stock.dto.StockRequestDto;
import com.clothing.inventory.stock.dto.StockResponseDto;

import java.util.List;

public interface StockService {

    StockResponseDto stockIn(
            StockRequestDto requestDto);

    StockResponseDto stockOut(StockRequestDto requestDto);

    List<StockResponseDto> getStockHistory(Long productId);

    List<LowStockResponseDto> getLowStockProducts();

    List<StockResponseDto> getAllStockHistory();

    void stockInFromPurchase(Product product, Integer quantity);
}
