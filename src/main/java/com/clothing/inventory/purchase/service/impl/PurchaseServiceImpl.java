package com.clothing.inventory.purchase.service.impl;

import com.clothing.inventory.exception.InvalidPurchaseStateException;
import com.clothing.inventory.exception.ResourceNotFoundException;
import com.clothing.inventory.product.entity.Product;
import com.clothing.inventory.product.repository.ProductRepository;
import com.clothing.inventory.purchase.dto.PurchaseItemRequestDto;
import com.clothing.inventory.purchase.dto.PurchaseRequestDto;
import com.clothing.inventory.purchase.dto.PurchaseResponseDto;
import com.clothing.inventory.purchase.entity.Purchase;
import com.clothing.inventory.purchase.entity.PurchaseItem;
import com.clothing.inventory.purchase.enums.PurchaseStatus;
import com.clothing.inventory.purchase.mapper.PurchaseMapper;
import com.clothing.inventory.purchase.repository.PurchaseRepository;
import com.clothing.inventory.purchase.service.PurchaseService;
import com.clothing.inventory.stock.entity.Stock;
import com.clothing.inventory.stock.service.StockService;
import com.clothing.inventory.supplier.entity.Supplier;
import com.clothing.inventory.supplier.repository.SupplierRepo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierRepo supplierRepo;
    private final ProductRepository productRepository;
    private final PurchaseMapper purchaseMapper;
    private final StockService stockService;

    public PurchaseServiceImpl(
            PurchaseRepository purchaseRepository,
            SupplierRepo supplierRepo,
            ProductRepository productRepository,
            PurchaseMapper purchaseMapper,
            StockService stockService) {

        this.purchaseRepository = purchaseRepository;
        this.supplierRepo = supplierRepo;
        this.productRepository = productRepository;
        this.purchaseMapper = purchaseMapper;
        this.stockService = stockService;
    }

    @Override
    @Transactional
    public PurchaseResponseDto createPurchase(PurchaseRequestDto request) {

        // 1. Find supplier
        Supplier supplier = supplierRepo
                .findByIdAndDeletedFalse(request.getSupplierId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found"));

        // 2. Create purchase
        Purchase purchase = new Purchase();

        purchase.setSupplier(supplier);
        purchase.setStatus(PurchaseStatus.PENDING);

        BigDecimal totalAmount = BigDecimal.ZERO; // initially -> 0

        // 3. Create every purchase item
        for (PurchaseItemRequestDto itemRequest : request.getItems()) {

            // Find product
            Product product = productRepository
                    .findByIdAndDeletedFalse(itemRequest.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Product not found"));

            // Calculate subtotal
            BigDecimal subtotal = itemRequest.getUnitCost()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            // Create item
            PurchaseItem item = new PurchaseItem();

            item.setPurchase(purchase);
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitCost(itemRequest.getUnitCost());
            item.setSubtotal(subtotal);

            // Add item to purchase
            purchase.getItems().add(item);

            // Add subtotal to total
            totalAmount = totalAmount.add(subtotal);
        }

        // 4. Set final total
        purchase.setTotalAmount(totalAmount);

        // 5. Save
        Purchase savedPurchase = purchaseRepository.save(purchase);

        // 6. Return response
        return purchaseMapper.toResponse(savedPurchase);
    }

    @Override
    public PurchaseResponseDto getPurchaseById(Long id) {

        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase not found"));

        return purchaseMapper.toResponse(purchase);
    }

    @Override
    public Page<PurchaseResponseDto> getAllPurchases(
            int page,
            int size,
            String search,
            PurchaseStatus status) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        Page<Purchase> purchases;

        boolean hasSearch = search != null && !search.isBlank();

        if (hasSearch && status != null) {
            purchases =
                    purchaseRepository.findBySupplier_NameContainingIgnoreCaseAndStatus(
                            search,
                            status,
                            pageable
                    );

        } else if (hasSearch) {
            purchases =
                    purchaseRepository.findBySupplier_NameContainingIgnoreCase(
                            search,
                            pageable
                    );

        } else if (status != null) {

            purchases =
                    purchaseRepository.findByStatus(
                            status,
                            pageable
                    );

        } else {
            purchases = purchaseRepository.findAll(pageable);
        }

        return purchases.map(purchaseMapper::toResponse);
    }

    @Override
    @Transactional
    public PurchaseResponseDto updatePurchase(
            Long id,
            PurchaseRequestDto request) {

        // 1. Find purchase
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase not found"));

        // 2. Only PENDING purchases can be edited
        if (purchase.getStatus() != PurchaseStatus.PENDING) {
            throw new InvalidPurchaseStateException(
                    "Only pending purchases can be updated"
            );
        }

        // 3. Find supplier
        Supplier supplier = supplierRepo
                .findByIdAndDeletedFalse(request.getSupplierId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found"));

        purchase.setSupplier(supplier);

        // 4. Remove old purchase items
        purchase.getItems().clear();

        BigDecimal totalAmount = BigDecimal.ZERO;

        // 5. Create updated items
        for (PurchaseItemRequestDto itemRequest : request.getItems()) {

            Product product = productRepository
                    .findByIdAndDeletedFalse(itemRequest.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Product not found: "
                                            + itemRequest.getProductId()
                            ));

            BigDecimal subtotal = itemRequest.getUnitCost()
                    .multiply(
                            BigDecimal.valueOf(itemRequest.getQuantity())
                    );

            PurchaseItem item = new PurchaseItem();

            item.setPurchase(purchase);
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitCost(itemRequest.getUnitCost());
            item.setSubtotal(subtotal);

            purchase.getItems().add(item);

            totalAmount = totalAmount.add(subtotal);
        }

        // 6. Recalculate total
        purchase.setTotalAmount(totalAmount);

        // 7. Save
        Purchase updatedPurchase = purchaseRepository.save(purchase);

        return purchaseMapper.toResponse(updatedPurchase);
    }

    @Override
    @Transactional
    public PurchaseResponseDto receivePurchase(Long id) {

        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Purchase not found with id: " + id
                        ));

        if (purchase.getStatus() != PurchaseStatus.PENDING) {
            throw new InvalidPurchaseStateException(
                    "Only pending purchases can be received"
            );
        }

        for (PurchaseItem item : purchase.getItems()) {

            stockService.stockInFromPurchase(
                    item.getProduct(),
                    item.getQuantity()
            );
        }

        purchase.setStatus(PurchaseStatus.RECEIVED);
        purchase.setReceivedAt(LocalDateTime.now());

        Purchase savedPurchase =
                purchaseRepository.save(purchase);

        return purchaseMapper.toResponse(savedPurchase);
    }

    @Override
    @Transactional
    public PurchaseResponseDto cancelPurchase(Long id) {

        // Find purchase
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Purchase not found with id: " + id
                        )
                );

        // Only PENDING purchases can be cancelled
        if (purchase.getStatus() != PurchaseStatus.PENDING) {
            throw new InvalidPurchaseStateException(
                    "Only pending purchases can be cancelled"
            );
        }

        // Change status
        purchase.setStatus(PurchaseStatus.CANCELLED);

        // Save
        Purchase savedPurchase = purchaseRepository.save(purchase);

        return purchaseMapper.toResponse(savedPurchase);
    }
}
