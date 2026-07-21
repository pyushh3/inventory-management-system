package com.clothing.inventory.brand.service.impl;

import com.clothing.inventory.brand.dto.BrandRequestDto;
import com.clothing.inventory.brand.dto.BrandResponseDto;
import com.clothing.inventory.brand.dto.UpdateBrandRequestDto;
import com.clothing.inventory.brand.entity.Brand;
import com.clothing.inventory.brand.enums.BrandStatus;
import com.clothing.inventory.brand.mapper.BrandMapper;
import com.clothing.inventory.brand.repository.BrandRepo;
import com.clothing.inventory.brand.service.BrandService;
import com.clothing.inventory.exception.DuplicateResourceException;
import com.clothing.inventory.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepo br;
    private final BrandMapper bm;

    public BrandServiceImpl(BrandRepo br, BrandMapper bm) {
        this.br = br;
        this.bm = bm;
    }

    public BrandResponseDto createBrand(BrandRequestDto requestDto) {

        if(nameExist(requestDto.getName())) {
            throw new DuplicateResourceException
                    ("Brand with name " + requestDto.getName() + " already exists");
        }

        Brand brand = bm.toEntity(requestDto);
        Brand savedBrand = br.save(brand);

        return bm.toResponse(savedBrand);
    }

    public BrandResponseDto getBrandById(Long id) {

        Brand brand = br.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new ResourceNotFoundException("Brand with id " + id + " not found"));

        return bm.toResponse(brand);
    }

    public Page<BrandResponseDto> getAllBrands(int page, int size, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by("createdAt").ascending()
                : Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Brand> brandPage = br.findByDeletedFalse(pageable);

        return brandPage.map(bm::toResponse);
    }

    public BrandResponseDto updateBrand(Long id, UpdateBrandRequestDto updateReq) {

        Brand brand = br.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new ResourceNotFoundException("Brand with id " + id + " not found"));

        bm.updateEntity(updateReq, brand);

        Brand savedBrand = br.save(brand);

        return bm.toResponse(savedBrand);
    }

    public void softDelete(Long id) {

        Brand brandToSave = br.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new ResourceNotFoundException("Brand with id " + id + " not found"));

        brandToSave.setDeleted(true);
        brandToSave.setStatus(BrandStatus.INACTIVE);

        br.save(brandToSave);
    }

    public List<BrandResponseDto> searchBrandByName(String name) {

        List<Brand> brands =
                br.findByDeletedFalseAndNameContainingIgnoreCase(name);

        return brands.stream()
                .map(bm::toResponse)
                .toList();
    }

    public boolean nameExist(String name) {
        return br.existsByNameIgnoreCase(name);
    }
}
