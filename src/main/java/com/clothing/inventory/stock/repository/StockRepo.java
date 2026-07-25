package com.clothing.inventory.stock.repository;

import com.clothing.inventory.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepo extends JpaRepository<Stock, Long> {

    List<Stock> findByProductIdOrderByCreatedAtDesc(Long productId);

    List<Stock> findAllByOrderByCreatedAtDesc();

}
