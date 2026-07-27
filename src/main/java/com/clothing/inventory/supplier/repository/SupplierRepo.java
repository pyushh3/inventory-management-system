package com.clothing.inventory.supplier.repository;

import com.clothing.inventory.supplier.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepo extends JpaRepository<Supplier, Long> {


    boolean existsByEmailIgnoreCase(String email);

    Optional<Supplier> findByIdAndDeletedFalse(Long id);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    Page<Supplier> findByDeletedFalseAndNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    long countByDeletedFalse();
}

