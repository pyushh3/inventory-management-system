package com.clothing.inventory.category.service.implementation;

import com.clothing.inventory.category.dto.CategoryRequestDto;
import com.clothing.inventory.category.dto.CategoryResponseDto;
import com.clothing.inventory.category.dto.UpdateCategoryRequestDto;
import com.clothing.inventory.category.entity.Category;
import com.clothing.inventory.category.enums.CategoryStatus;
import com.clothing.inventory.exception.DuplicateResourceException;
import com.clothing.inventory.exception.ResourceNotFoundException;
import com.clothing.inventory.category.mapper.CategoryMapper;
import com.clothing.inventory.category.repository.CategoryRepo;
import com.clothing.inventory.category.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo cr;
    private final CategoryMapper cm;

    //dependency injection
    public CategoryServiceImpl(CategoryRepo cr, CategoryMapper cm) {
        this.cr = cr;
        this.cm = cm;
    }


    //create category
    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {

        if(nameExist(requestDto.getName())) {
            throw new DuplicateResourceException
                    ("category with name " + requestDto.getName() + " already exists");
        }

        Category category = cm.toEntity(requestDto); //dto to entity mapping
        Category savedCategory = cr.save(category); // entity stored in database and return the saved entity

        return cm.toResponse(savedCategory); // send dto response to client(Postman)
    }

    //get category by id
    @Override
    public CategoryResponseDto getCategoryById(Long id) {

//        Optional<Category> optionalCategory = cr.findById(id);

//        if(optionalCategory.isEmpty()) {
//            return null;
//        }

//        Category category = optionalCategory.get();
//        return cm.toResponse(category);

        Category category = cr.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new ResourceNotFoundException("Category with id " + id + " not found"));

        return cm.toResponse(category);
    }


    //get all categories
    @Override
    public Page<CategoryResponseDto> getAllCategory(int page, int size, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by("createdAt").ascending()
                : Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> categoryPage = cr.findByDeletedFalse(pageable);

        return categoryPage.map(cm::toResponse);
    }

    //update category
    @Override
    public CategoryResponseDto updateCategory(Long id, UpdateCategoryRequestDto updateReq) {

//        Optional<Category> optionalCategory = cr.findByIdAndDeletedFalse(id);
//
//        if(optionalCategory.isEmpty()) {
//            return null;
//        }
//
//        Category category = optionalCategory.get();

        Category category = cr.findByIdAndDeletedFalse(id).orElseThrow(() ->
                new ResourceNotFoundException("Category with id " + id + " not found"));

        cm.updateEntity(updateReq, category);

        Category savedCategory = cr.save(category);
        return cm.toResponse(savedCategory);
    }

    // hard delete
    public void deleteCategory(Long id) {
        Category category = cr.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category with id " + id + " not found"));

        cr.delete(category);
    }

    //soft delete
    public void softDelete(Long id) {


        Category categoryToSave = cr.findByIdAndDeletedFalse(id).orElseThrow(()->
                new ResourceNotFoundException("Category with id " + id + " not found"));

        categoryToSave.setDeleted(true);
        categoryToSave.setStatus(CategoryStatus.INACTIVE);

        cr.save(categoryToSave);
    }

    @Override
    public List<CategoryResponseDto> searchCategoryByName(String name) {

        List<Category> categories =
                cr.findByDeletedFalseAndNameContainingIgnoreCase(name);

        return categories.stream()
                .map(cm::toResponse)
                .toList();
    }

    @Override
    public Page<CategoryResponseDto> getCategoryByStatus(CategoryStatus status, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Category> categoryPage =
                cr.findByStatusAndDeletedFalse(status, pageable);

        return categoryPage.map(cm::toResponse);
    }

    //helper method
    public boolean nameExist(String name) {
        return cr.existsByNameIgnoreCase(name);
    }
}
