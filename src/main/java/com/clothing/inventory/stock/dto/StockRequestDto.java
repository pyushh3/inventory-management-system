package com.clothing.inventory.stock.dto;

import com.clothing.inventory.stock.enums.StockReason;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class StockRequestDto {


    @NotNull(message = "Product id is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;

    @NotNull(message = "Reason is required")
    private StockReason reason;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
}
