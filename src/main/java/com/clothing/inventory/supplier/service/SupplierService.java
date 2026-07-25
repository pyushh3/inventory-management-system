package com.clothing.inventory.supplier.service;

import com.clothing.inventory.supplier.dto.SupplierRequestDto;
import com.clothing.inventory.supplier.dto.SupplierResponseDto;
import org.springframework.data.domain.Page;

public interface SupplierService {

    SupplierResponseDto createSupplier(SupplierRequestDto requestDto);


    Page<SupplierResponseDto> getAllSuppliers(
            int page,
            int size,
            String search,
            String direction
    );
    SupplierResponseDto getSupplierById(Long id);

    SupplierResponseDto updateSupplier(
            Long id,
            SupplierRequestDto requestDto
    );

    void deleteSupplier(Long id);
}
