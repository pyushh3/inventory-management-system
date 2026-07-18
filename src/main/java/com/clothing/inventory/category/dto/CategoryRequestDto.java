package com.clothing.inventory.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequestDto {

    @NotBlank(message = "Name Cannot Ee Blank")
    private String name;
    @NotBlank(message = "Description Cannot Be Blank")
    @Size(max = 100, message = "Description should not contain more than 100 words")
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

