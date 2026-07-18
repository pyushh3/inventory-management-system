package com.clothing.inventory.category.repository;

import com.clothing.inventory.category.entity.Category;
import com.clothing.inventory.category.enums.CategoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    Optional<Category> findByIdAndDeletedFalse(Long id);
    Page<Category> findByDeletedFalse(Pageable pageable);

    boolean existsByNameIgnoreCase(String name);

    List<Category>findByDeletedFalseAndNameContainingIgnoreCase(String name);

    Page<Category> findByStatusAndDeletedFalse(
            CategoryStatus status,
            Pageable pageable
    );
}
