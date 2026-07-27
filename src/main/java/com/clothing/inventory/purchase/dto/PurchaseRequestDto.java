package com.clothing.inventory.purchase.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PurchaseRequestDto {

    @NotNull(message = "Supplier is required")
    private Long supplierId;

    @Valid
    @NotEmpty(message = "At least one product is required")
    private List<PurchaseItemRequestDto> items;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public List<PurchaseItemRequestDto> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItemRequestDto> items) {
        this.items = items;
    }
}
