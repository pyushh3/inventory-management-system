package com.clothing.inventory.brand.controller;

import com.clothing.inventory.brand.dto.BrandRequestDto;
import com.clothing.inventory.brand.dto.BrandResponseDto;
import com.clothing.inventory.brand.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    private final BrandService bs;

    public BrandController(BrandService bs) {
        this.bs = bs;
    }

    @PostMapping
    public BrandResponseDto createBrand(@Valid @RequestBody BrandRequestDto requestDto) {

        return bs.createBrand(requestDto);
    }


    @GetMapping("/{id}")
    public BrandResponseDto getBrand(@PathVariable Long id) {

       return bs.getBrandById(id);
    }

}
