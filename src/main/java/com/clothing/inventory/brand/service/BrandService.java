package com.clothing.inventory.brand.service;

import com.clothing.inventory.brand.dto.BrandRequestDto;
import com.clothing.inventory.brand.dto.BrandResponseDto;

public interface BrandService {

    BrandResponseDto createBrand(BrandRequestDto requestDto);

    BrandResponseDto getBrandById(Long id);
}
