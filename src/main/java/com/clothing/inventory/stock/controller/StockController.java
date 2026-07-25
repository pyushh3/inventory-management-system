package com.clothing.inventory.stock.controller;

import com.clothing.inventory.product.dto.ProductResponseDto;
import com.clothing.inventory.stock.dto.LowStockResponseDto;
import com.clothing.inventory.stock.dto.StockRequestDto;
import com.clothing.inventory.stock.dto.StockResponseDto;
import com.clothing.inventory.stock.service.StockService;
import com.clothing.inventory.stock.service.impl.StockServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/in")
    public ResponseEntity<StockResponseDto> stockIn(
            @Valid @RequestBody StockRequestDto requestDto) {

        return ResponseEntity.ok(
                stockService.stockIn(requestDto)
        );
    }

    @PostMapping("/out")
    public ResponseEntity<StockResponseDto> stockOut(
            @Valid @RequestBody StockRequestDto requestDto) {

        return ResponseEntity.ok(
                stockService.stockOut(requestDto)
        );
    }

    @GetMapping("/history/{productId}")
    public ResponseEntity<List<StockResponseDto>> getStockHistory(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                stockService.getStockHistory(productId)
        );
    }

    @GetMapping("/history")
    public ResponseEntity<List<StockResponseDto>> getAllStockHistory() {

        return ResponseEntity.ok(
                stockService.getAllStockHistory()
        );
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<LowStockResponseDto>> getLowStockProducts() {

        return ResponseEntity.ok(
                stockService.getLowStockProducts()
        );
    }
}
