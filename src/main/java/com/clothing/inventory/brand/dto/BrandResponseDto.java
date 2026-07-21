package com.clothing.inventory.brand.dto;

import com.clothing.inventory.brand.enums.BrandStatus;
import com.clothing.inventory.category.enums.CategoryStatus;

public class BrandResponseDto {

    private Long id;
    private String name;
    private String description;
    private BrandStatus status;


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

    public BrandStatus getStatus() {
        return status;
    }

    public void setStatus(BrandStatus status) {
        this.status = status;
    }
}
