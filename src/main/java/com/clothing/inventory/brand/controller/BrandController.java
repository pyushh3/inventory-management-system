package com.clothing.inventory.brand.controller;

import com.clothing.inventory.brand.dto.BrandRequestDto;
import com.clothing.inventory.brand.dto.BrandResponseDto;
import com.clothing.inventory.brand.dto.UpdateBrandRequestDto;
import com.clothing.inventory.brand.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<BrandResponseDto> getBrand(@PathVariable Long id) {


        BrandResponseDto responseDto = bs.getBrandById(id);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<Page<BrandResponseDto>> getAllCategories(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "3") int size,
                                                                   @RequestParam(defaultValue = "asc") String sort) {
        return ResponseEntity.ok(bs.getAllBrands(page, size, sort));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponseDto> updateBrand(@PathVariable Long id, @RequestBody UpdateBrandRequestDto updateReq) {

        return ResponseEntity.ok(bs.updateBrand(id, updateReq));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        bs.softDelete(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BrandResponseDto>> searchBrandByName(@RequestParam String name) {

        List<BrandResponseDto> brands =
                bs.searchBrandByName(name);

        return ResponseEntity.ok(brands);
    }


}
