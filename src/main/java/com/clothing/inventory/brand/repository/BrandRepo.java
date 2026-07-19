package com.clothing.inventory.brand.repository;

import com.clothing.inventory.brand.entity.Brand;
import com.clothing.inventory.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepo extends JpaRepository<Brand, Long> {

    Optional<Brand> findByIdAndDeletedFalse(Long id);


}
