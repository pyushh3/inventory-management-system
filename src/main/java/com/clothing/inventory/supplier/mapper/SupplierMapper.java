package com.clothing.inventory.supplier.mapper;

import com.clothing.inventory.category.entity.Category;
import com.clothing.inventory.supplier.dto.SupplierRequestDto;
import com.clothing.inventory.supplier.dto.SupplierResponseDto;
import com.clothing.inventory.supplier.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public Supplier toEntity(SupplierRequestDto dto) {

        Supplier supplier = new Supplier();

        supplier.setName(dto.getName());
        supplier.setContactPerson(dto.getContactPerson());
        supplier.setPhone(dto.getPhone());
        supplier.setEmail(dto.getEmail());
        supplier.setAddress(dto.getAddress());

        return supplier;
    }

    public SupplierResponseDto toResponse(Supplier supplier) {
        SupplierResponseDto supplierResp = new SupplierResponseDto();

        supplierResp.setName(supplier.getName());
        supplierResp.setContactPerson(supplier.getContactPerson());
        supplierResp.setPhone(supplier.getPhone());
        supplierResp.setEmail(supplier.getEmail());
        supplierResp.setAddress(supplier.getAddress());
        supplierResp.setStatus(supplier.getStatus());
        supplierResp.setId(supplier.getId());

        return supplierResp;
    }
}
