package com.clothing.inventory.brand.repository;

import com.clothing.inventory.brand.entity.Brand;
import com.clothing.inventory.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepo extends JpaRepository<Brand, Long> {

    Optional<Brand> findByIdAndDeletedFalse(Long id);

    Page<Brand> findByDeletedFalse(Pageable pageable);

    List<Brand> findByDeletedFalseAndNameContainingIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

}
