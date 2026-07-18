package com.clothing.inventory.category.mapper;

import com.clothing.inventory.category.dto.CategoryRequestDto;
import com.clothing.inventory.category.dto.CategoryResponseDto;
import com.clothing.inventory.category.dto.UpdateCategoryRequestDto;
import com.clothing.inventory.category.entity.Category;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDto dto) {

        Category category = new Category();

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }


    public CategoryResponseDto toResponse(Category category) {
        CategoryResponseDto responseDto = new CategoryResponseDto();

        responseDto.setName(category.getName());
        responseDto.setDescription(category.getDescription());
        responseDto.setId(category.getId());
        responseDto.setStatus(category.getStatus());

        return responseDto;
    }

    public void updateEntity(UpdateCategoryRequestDto dto, Category category) {

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

    }
}
