package com.clothing.inventory.brand.service;

import com.clothing.inventory.brand.dto.BrandRequestDto;
import com.clothing.inventory.brand.dto.BrandResponseDto;
import com.clothing.inventory.brand.dto.UpdateBrandRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {

    BrandResponseDto createBrand(BrandRequestDto requestDto);

    BrandResponseDto getBrandById(Long id);

    Page<BrandResponseDto> getAllBrands(int page, int size, String sort);

    BrandResponseDto updateBrand(Long id, UpdateBrandRequestDto requestDto);

    public void softDelete(Long id);

    public boolean nameExist(String name);

    List<BrandResponseDto> searchBrandByName(String name);




}
