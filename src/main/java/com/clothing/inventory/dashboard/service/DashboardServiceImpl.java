package com.clothing.inventory.dashboard.service;

import com.clothing.inventory.category.repository.CategoryRepo;
import com.clothing.inventory.dashboard.dto.DashboardResponseDto;
import com.clothing.inventory.dashboard.dto.LowStockDto;
import com.clothing.inventory.dashboard.dto.RecentPurchaseDto;
import com.clothing.inventory.product.entity.Product;
import com.clothing.inventory.product.repository.ProductRepository;
import com.clothing.inventory.purchase.entity.Purchase;
import com.clothing.inventory.purchase.enums.PurchaseStatus;
import com.clothing.inventory.purchase.repository.PurchaseRepository;
import com.clothing.inventory.supplier.repository.SupplierRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {



    private final ProductRepository productRepo;
    private final CategoryRepo categoryRepo;
    private final SupplierRepo supplierRepo;
    private final PurchaseRepository purchaseRepo;

    public DashboardServiceImpl(
            ProductRepository productRepo,
            CategoryRepo categoryRepo,
            SupplierRepo supplierRepo,
            PurchaseRepository purchaseRepo) {

        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.supplierRepo = supplierRepo;
        this.purchaseRepo = purchaseRepo;
    }

    @Override
    public DashboardResponseDto getDashboard() {

        DashboardResponseDto response = new DashboardResponseDto();

        // Dashboard cards
        response.setTotalProducts(
                productRepo.countByDeletedFalse()
        );

        response.setTotalStock(
                productRepo.getTotalStock()
        );

        response.setTotalCategories(
                categoryRepo.countByDeletedFalse()
        );

        response.setTotalSuppliers(
                supplierRepo.countByDeletedFalse()
        );

        // Low stock products
        List<Product> lowStockProducts =
                productRepo
                        .findByDeletedFalseAndQuantityLessThanOrderByQuantityAsc(10);

        List<LowStockDto> lowStockDtos = lowStockProducts.stream()
                .map(product -> {

                    LowStockDto dto = new LowStockDto();

                    dto.setProductId(product.getId());
                    dto.setProductName(product.getName());
                    dto.setSku(product.getSku());
                    dto.setQuantity(product.getQuantity());

                    return dto;
                })
                .toList();

        response.setLowStockProducts(lowStockDtos);

        List<Purchase> purchases =
                purchaseRepo.findTop5ByStatusOrderByCreatedAtDesc(
                        PurchaseStatus.RECEIVED
                );

        List<RecentPurchaseDto> purchaseDtos = purchases.stream()
                .map(purchase -> {

                    RecentPurchaseDto dto = new RecentPurchaseDto();

                    dto.setPurchaseId(purchase.getId());

                    dto.setSupplierName(
                            purchase.getSupplier().getName()
                    );

                    int totalQuantity = purchase.getItems()
                            .stream()
                            .mapToInt(item -> item.getQuantity())
                            .sum();

                    dto.setTotalQuantity(totalQuantity);

                    dto.setPurchaseDate(
                            purchase.getCreatedAt()
                    );

                    return dto;
                })
                .toList();

        response.setRecentPurchases(purchaseDtos);

        return response;
    }
}
