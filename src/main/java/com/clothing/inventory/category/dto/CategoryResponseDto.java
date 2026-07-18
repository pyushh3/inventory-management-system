package com.clothing.inventory.category.dto;

import com.clothing.inventory.category.enums.CategoryStatus;

import java.time.LocalDateTime;

public class CategoryResponseDto {

    private Long id;
    private String name;
    private String description;
    private CategoryStatus status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }
}

