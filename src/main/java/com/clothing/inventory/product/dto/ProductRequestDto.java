package com.clothing.inventory.product.dto;

import com.clothing.inventory.product.enums.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ProductRequestDto {

    @NotBlank(message = "Product name is required.")
    private String name;

    @NotBlank(message = "SKU is required.")
    private String sku;

    @NotNull(message = "Price is required.")
    @Positive(message = "Price must be greater than 0.")
    private BigDecimal price;

    @NotNull(message = "Quantity is required.")
    @Min(value = 0, message = "Quantity cannot be negative.")
    private Integer quantity;

    @NotBlank(message = "Size is required.")
    private String size;

    @NotBlank(message = "Color is required.")
    private String color;

    @NotBlank(message = "Material is required.")
    private String material;

    @NotNull(message = "Gender is required.")
    private Gender gender;

    @NotNull(message = "Category is required.")
    private Long categoryId;

    @NotNull(message = "Brand is required.")
    private Long brandId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}

