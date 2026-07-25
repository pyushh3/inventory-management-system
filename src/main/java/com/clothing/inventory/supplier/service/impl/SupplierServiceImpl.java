package com.clothing.inventory.supplier.service.impl;

import com.clothing.inventory.exception.DuplicateResourceException;
import com.clothing.inventory.exception.ResourceNotFoundException;
import com.clothing.inventory.supplier.dto.SupplierRequestDto;
import com.clothing.inventory.supplier.dto.SupplierResponseDto;
import com.clothing.inventory.supplier.entity.Supplier;
import com.clothing.inventory.supplier.mapper.SupplierMapper;
import com.clothing.inventory.supplier.repository.SupplierRepo;
import com.clothing.inventory.supplier.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {

    private SupplierRepo supplierRepository;
    private SupplierMapper supplierMapper;

    public SupplierServiceImpl(SupplierRepo supplierRepo, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepo;
        this.supplierMapper = supplierMapper;
    }

    public SupplierResponseDto createSupplier(SupplierRequestDto requestDto) {
        if (supplierRepository.existsByEmailIgnoreCase(requestDto.getEmail())) {
            throw new DuplicateResourceException(
                    "Supplier with this email already exists"
            );
        }

        Supplier supplier = supplierMapper.toEntity(requestDto);

        Supplier savedSupplier = supplierRepository.save(supplier);

        return supplierMapper.toResponse(savedSupplier);
    }
    @Override
    public SupplierResponseDto getSupplierById(Long id) {

        Supplier supplier = supplierRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Supplier not found with id: " + id
                        )
                );

        return supplierMapper.toResponse(supplier);
    }

    @Override
    public Page<SupplierResponseDto> getAllSuppliers(
            int page,
            int size,
            String search,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by("createdAt").descending()
                : Sort.by("createdAt").ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        String searchValue = search == null ? "" : search.trim();

        Page<Supplier> suppliers =
                supplierRepository
                        .findByDeletedFalseAndNameContainingIgnoreCase(
                                searchValue,
                                pageable
                        );

        return suppliers.map(supplierMapper::toResponse);
    }

    @Override
    public SupplierResponseDto updateSupplier(
            Long id,
            SupplierRequestDto requestDto) {

        Supplier supplier = supplierRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Supplier not found with id: " + id
                        )
                );

        if (supplierRepository.existsByEmailIgnoreCaseAndIdNot(
                requestDto.getEmail(), id)) {

            throw new DuplicateResourceException(
                    "Supplier with this email already exists"
            );
        }

        supplier.setName(requestDto.getName());
        supplier.setContactPerson(requestDto.getContactPerson());
        supplier.setPhone(requestDto.getPhone());
        supplier.setEmail(requestDto.getEmail());
        supplier.setAddress(requestDto.getAddress());

        Supplier updatedSupplier = supplierRepository.save(supplier);

        return supplierMapper.toResponse(updatedSupplier);
    }

    @Override
    public void deleteSupplier(Long id) {

        Supplier supplier = supplierRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Supplier not found with id: " + id
                        )
                );

        supplier.setDeleted(true);

        supplierRepository.save(supplier);
    }
}
