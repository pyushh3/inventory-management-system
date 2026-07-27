package com.clothing.inventory.purchase.mapper;

import com.clothing.inventory.purchase.dto.PurchaseItemResponseDto;
import com.clothing.inventory.purchase.dto.PurchaseResponseDto;
import com.clothing.inventory.purchase.entity.Purchase;
import com.clothing.inventory.purchase.entity.PurchaseItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseMapper {

    public PurchaseResponseDto toResponse(Purchase purchase) {

        PurchaseResponseDto response = new PurchaseResponseDto();

        response.setId(purchase.getId());

        response.setSupplierId(purchase.getSupplier().getId());
        response.setSupplierName(purchase.getSupplier().getName());

        response.setStatus(purchase.getStatus());
        response.setTotalAmount(purchase.getTotalAmount());

        response.setCreatedAt(purchase.getCreatedAt());
        response.setReceivedAt(purchase.getReceivedAt());

        List<PurchaseItemResponseDto> items = purchase.getItems()
                .stream()
                .map(this::toItemResponse)
                .toList();

        response.setItems(items);

        return response;
    }


    public PurchaseItemResponseDto toItemResponse(PurchaseItem item) {

        PurchaseItemResponseDto response = new PurchaseItemResponseDto();

        response.setId(item.getId());

        response.setProductId(item.getProduct().getId());
        response.setProductName(item.getProduct().getName());
        response.setSku(item.getProduct().getSku());

        response.setQuantity(item.getQuantity());
        response.setUnitCost(item.getUnitCost());
        response.setSubtotal(item.getSubtotal());

        return response;
    }


}
