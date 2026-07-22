package com.clothing.inventory.category.service;

import com.clothing.inventory.category.dto.CategoryRequestDto;
import com.clothing.inventory.category.dto.CategoryResponseDto;
import com.clothing.inventory.category.dto.UpdateCategoryRequestDto;
import com.clothing.inventory.category.enums.CategoryStatus;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CategoryService {


    CategoryResponseDto createCategory(CategoryRequestDto requestDto);

    CategoryResponseDto getCategoryById(Long id);

    Page<CategoryResponseDto> getAllCategory(int page, int size, String sort);

    CategoryResponseDto updateCategory(Long id,UpdateCategoryRequestDto updateRequestDto);

    public void deleteCategory(Long id);

    public void softDelete(Long id);

    public boolean nameExist(String name);

    List<CategoryResponseDto> searchCategoryByName(String name);

    Page<CategoryResponseDto> getCategoryByStatus(CategoryStatus status, int page, int size);

}
