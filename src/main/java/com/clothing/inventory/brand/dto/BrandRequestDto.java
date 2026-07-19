package com.clothing.inventory.brand.dto;

import jakarta.validation.constraints.NotBlank;

public class BrandRequestDto {

    @NotBlank(message = "Name Cannot Be Blank")
    private String name;
    @NotBlank(message = "Description Cannot Be Blank")
    private String description;

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
}
