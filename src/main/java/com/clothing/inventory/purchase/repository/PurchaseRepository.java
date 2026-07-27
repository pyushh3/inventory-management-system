package com.clothing.inventory.purchase.repository;

import com.clothing.inventory.purchase.entity.Purchase;
import com.clothing.inventory.purchase.enums.PurchaseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    Page<Purchase> findBySupplier_NameContainingIgnoreCase(
            String search,
            Pageable pageable
    );

    Page<Purchase> findByStatus(
            PurchaseStatus status,
            Pageable pageable
    );

    Page<Purchase> findBySupplier_NameContainingIgnoreCaseAndStatus(
            String search,
            PurchaseStatus status,
            Pageable pageable
    );
}
