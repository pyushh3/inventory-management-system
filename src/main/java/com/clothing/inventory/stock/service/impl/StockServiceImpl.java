package com.clothing.inventory.stock.service.impl;

import com.clothing.inventory.exception.InsufficientStockException;
import com.clothing.inventory.exception.ResourceNotFoundException;
import com.clothing.inventory.product.dto.ProductResponseDto;
import com.clothing.inventory.product.entity.Product;
import com.clothing.inventory.product.mapper.ProductMapper;
import com.clothing.inventory.product.repository.ProductRepository;
import com.clothing.inventory.stock.dto.LowStockResponseDto;
import com.clothing.inventory.stock.dto.StockRequestDto;
import com.clothing.inventory.stock.dto.StockResponseDto;
import com.clothing.inventory.stock.entity.Stock;
import com.clothing.inventory.stock.enums.StockMovementType;
import com.clothing.inventory.stock.mapper.StockMapper;
import com.clothing.inventory.stock.repository.StockRepo;
import com.clothing.inventory.stock.service.StockService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private static final int LOW_STOCK_THRESHOLD = 5;

    private StockRepo stockRepo;
    private StockMapper stockMapper;
    private ProductRepository productRepo;
    private ProductMapper productMapper;

    public StockServiceImpl(StockRepo stockRepo, StockMapper stockMapper,
                            ProductRepository productRepo, ProductMapper productMapper) {
        this.stockRepo = stockRepo;
        this.stockMapper = stockMapper;
        this.productRepo = productRepo;
        this.productMapper = productMapper;
    }

    @Transactional
    public StockResponseDto stockIn(StockRequestDto requestDto) {

        Product product = productRepo
                .findById(requestDto.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        // Increase current stock
        product.setQuantity(
                product.getQuantity() + requestDto.getQuantity()
        );

        productRepo.save(product);

        // DTO → Entity
        Stock movement =
                stockMapper.toEntity(requestDto);

        // Set relationships / backend controlled fields
        movement.setProduct(product);
        movement.setType(StockMovementType.STOCK_IN);

        // Save movement history
        Stock savedStock =
                stockRepo.save(movement);

        // Entity → Response DTO
        return stockMapper.toResponse(
                savedStock,
                product.getQuantity()
        );
    }
    @Transactional
    public StockResponseDto stockOut(StockRequestDto requestDto) {
        // 1. Find product
        Product product = productRepo
                .findById(requestDto.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        // 2. Check available stock
        if (product.getQuantity() < requestDto.getQuantity()) {
            throw new InsufficientStockException(
                    "Insufficient stock. Available quantity: "
                            + product.getQuantity()
            );
        }

        // 3. Reduce product quantity
        product.setQuantity(
                product.getQuantity() - requestDto.getQuantity()
        );

        productRepo.save(product);

        // 4. Convert request DTO → Stock entity
        Stock movement = stockMapper.toEntity(requestDto);

        // 5. Set backend-controlled fields
        movement.setProduct(product);
        movement.setType(StockMovementType.STOCK_OUT);

        // 6. Save movement history
        Stock savedMovement = stockRepo.save(movement);

        // 7. Convert Entity → Response DTO
        return stockMapper.toResponse(
                savedMovement,
                product.getQuantity()
        );
    }

    @Override
    public List<StockResponseDto> getStockHistory(Long productId) {

        Product product = productRepo
                .findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        List<Stock> stocks =
                stockRepo.findByProductIdOrderByCreatedAtDesc(productId);

        return stocks.stream()
                .map(stock ->
                        stockMapper.toResponse(
                                stock,
                                product.getQuantity()
                        )
                )
                .toList();
    }

    @Override
    public List<LowStockResponseDto> getLowStockProducts() {

        return productRepo
                .findByQuantityLessThanEqualAndDeletedFalse(
                        LOW_STOCK_THRESHOLD
                )
                .stream()
                .map(stockMapper::toLowStockResponse)
                .toList();
    }

}
