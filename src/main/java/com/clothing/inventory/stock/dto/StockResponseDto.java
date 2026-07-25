package com.clothing.inventory.stock.dto;

import com.clothing.inventory.stock.enums.StockMovementType;
import com.clothing.inventory.stock.enums.StockReason;

import java.time.LocalDateTime;

public class StockResponseDto {

    private Long id;

    private Long productId;
    private String productName;
    private String sku;

    private StockMovementType type;
    private Integer quantity;
    private StockReason reason;

    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public StockMovementType getType() {
        return type;
    }

    public void setType(StockMovementType type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public StockReason getReason() {
        return reason;
    }

    public void setReason(StockReason reason) {
        this.reason = reason;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
