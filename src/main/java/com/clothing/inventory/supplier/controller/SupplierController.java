package com.clothing.inventory.supplier.controller;

import com.clothing.inventory.supplier.dto.SupplierRequestDto;
import com.clothing.inventory.supplier.dto.SupplierResponseDto;
import com.clothing.inventory.supplier.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {


    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public SupplierResponseDto createSupplier(
            @Valid @RequestBody SupplierRequestDto requestDto) {

        return supplierService.createSupplier(requestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> getSupplierById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                supplierService.getSupplierById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<SupplierResponseDto>> getAllSuppliers(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "desc") String direction) {

        return ResponseEntity.ok(
                supplierService.getAllSuppliers(
                        page,
                        size,
                        search,
                        direction
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> updateSupplier(
            @PathVariable Long id,
            @Valid @RequestBody SupplierRequestDto requestDto) {

        return ResponseEntity.ok(
                supplierService.updateSupplier(id, requestDto)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {

        supplierService.deleteSupplier(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
