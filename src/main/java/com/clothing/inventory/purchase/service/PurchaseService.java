package com.clothing.inventory.purchase.service;

import com.clothing.inventory.product.entity.Product;
import com.clothing.inventory.purchase.dto.PurchaseRequestDto;
import com.clothing.inventory.purchase.dto.PurchaseResponseDto;
import com.clothing.inventory.purchase.enums.PurchaseStatus;
import org.springframework.data.domain.Page;

public interface PurchaseService {

    PurchaseResponseDto createPurchase(PurchaseRequestDto request);

    PurchaseResponseDto getPurchaseById(Long id);

    Page<PurchaseResponseDto> getAllPurchases(
            int page,
            int size,
            String search,
            PurchaseStatus status
    );

    PurchaseResponseDto updatePurchase(
            Long id,
            PurchaseRequestDto request
    );


    PurchaseResponseDto cancelPurchase(Long id);

    PurchaseResponseDto receivePurchase(Long id);
}
