package com.clothing.inventory.purchase.controller;

import com.clothing.inventory.purchase.dto.PurchaseRequestDto;
import com.clothing.inventory.purchase.dto.PurchaseResponseDto;
import com.clothing.inventory.purchase.enums.PurchaseStatus;
import com.clothing.inventory.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public PurchaseResponseDto createPurchase(
            @Valid @RequestBody PurchaseRequestDto request) {

        return purchaseService.createPurchase(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponseDto> getPurchaseById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                purchaseService.getPurchaseById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<PurchaseResponseDto>> getAllPurchases(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(required = false) String search,

            @RequestParam(required = false) PurchaseStatus status) {

        return ResponseEntity.ok(
                purchaseService.getAllPurchases(
                        page,
                        size,
                        search,
                        status
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseResponseDto> updatePurchase(
            @PathVariable Long id,
            @Valid @RequestBody PurchaseRequestDto request) {

        return ResponseEntity.ok(
                purchaseService.updatePurchase(id, request)
        );
    }

    @PatchMapping("/{id}/receive")
    public ResponseEntity<PurchaseResponseDto> receivePurchase(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                purchaseService.receivePurchase(id)
        );
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<PurchaseResponseDto> cancelPurchase(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                purchaseService.cancelPurchase(id)
        );
    }
}
