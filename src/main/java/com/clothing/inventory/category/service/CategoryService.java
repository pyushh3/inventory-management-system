package com.clothing.inventory.category.service;

import com.clothing.inventory.category.dto.CategoryRequestDto;
import com.clothing.inventory.category.dto.CategoryResponseDto;
import com.clothing.inventory.category.dto.UpdateCategoryRequestDto;

import java.util.List;


public interface CategoryService {


    CategoryResponseDto createCategory(CategoryRequestDto requestDto);

    CategoryResponseDto getCategoryById(Long id);

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto updateCategory(Long id,UpdateCategoryRequestDto updateRequestDto);

    public void deleteCategory(Long id);

    public void softDelete(Long id);

    public boolean nameExist(String name);

}
