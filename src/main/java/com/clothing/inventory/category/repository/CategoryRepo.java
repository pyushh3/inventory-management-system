package com.clothing.inventory.category.repository;

import com.clothing.inventory.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    Optional<Category> findByIdAndDeletedFalse(Long id);
    List<Category> findByDeletedFalse();

    boolean existsByNameIgnoreCase(String name);
}
