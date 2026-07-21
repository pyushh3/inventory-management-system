package com.clothing.inventory.brand.mapper;

import com.clothing.inventory.brand.dto.BrandRequestDto;
import com.clothing.inventory.brand.dto.BrandResponseDto;
import com.clothing.inventory.brand.dto.UpdateBrandRequestDto;
import com.clothing.inventory.brand.entity.Brand;
import com.clothing.inventory.category.dto.UpdateCategoryRequestDto;
import com.clothing.inventory.category.entity.Category;
import org.springframework.stereotype.Controller;

@Controller
public class BrandMapper {

    public Brand toEntity(BrandRequestDto requestDto) {
        Brand brand = new Brand();

        brand.setName(requestDto.getName());
        brand.setDescription(requestDto.getDescription());

        return brand;
    }

    public BrandResponseDto toResponse(Brand brand) {

        BrandResponseDto responseDto = new BrandResponseDto();

        responseDto.setId(brand.getId());
        responseDto.setName(brand.getName());
        responseDto.setDescription(brand.getDescription());
        responseDto.setStatus(brand.getStatus());

        return responseDto;
    }

    public void updateEntity(UpdateBrandRequestDto dto, Brand brand) {

        brand.setName(dto.getName());
        brand.setDescription(dto.getDescription());
    }
}
