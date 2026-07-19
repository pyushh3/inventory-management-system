package com.clothing.inventory.brand.service.impl;

import com.clothing.inventory.brand.dto.BrandRequestDto;
import com.clothing.inventory.brand.dto.BrandResponseDto;
import com.clothing.inventory.brand.entity.Brand;
import com.clothing.inventory.brand.mapper.BrandMapper;
import com.clothing.inventory.brand.repository.BrandRepo;
import com.clothing.inventory.brand.service.BrandService;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepo br;
    private final BrandMapper bm;

    public BrandServiceImpl(BrandRepo br, BrandMapper bm) {
        this.br = br;
        this.bm = bm;
    }

    public BrandResponseDto createBrand(BrandRequestDto requestDto) {


        Brand brand = bm.toEntity(requestDto);
        Brand savedBrand = br.save(brand);

        return bm.toResponse(savedBrand);
    }

    public BrandResponseDto getBrandById(Long id) {

        Brand  brand = br.findById(id).orElse(null);

        return bm.toResponse(brand);
    }
}
