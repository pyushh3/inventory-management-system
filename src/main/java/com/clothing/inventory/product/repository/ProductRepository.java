package com.clothing.inventory.product.repository;

import com.clothing.inventory.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {


    boolean existsBySkuIgnoreCase(String sku);

    Optional<Product> findByIdAndDeletedFalse(Long id);

    Page<Product> findByDeletedFalse(Pageable pageable);

    List<Product> findByQuantityLessThanEqualAndDeletedFalse(Integer quantity);


}
