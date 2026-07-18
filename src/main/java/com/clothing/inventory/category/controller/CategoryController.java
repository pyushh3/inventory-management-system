package com.clothing.inventory.category.controller;

import com.clothing.inventory.category.dto.CategoryRequestDto;
import com.clothing.inventory.category.dto.CategoryResponseDto;
import com.clothing.inventory.category.dto.UpdateCategoryRequestDto;
import com.clothing.inventory.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService cs;

    public CategoryController(CategoryService cs) {
        this.cs = cs;
    }

    @PostMapping
    public CategoryResponseDto createCategory(@Valid @RequestBody CategoryRequestDto categoryReq) {

        return cs.createCategory(categoryReq);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategory(@PathVariable Long id) {

        CategoryResponseDto categoryResp = cs.getCategoryById(id);

//        if(categoryResp == null) {
//            return ResponseEntity.notFound().build();
//        }

        return ResponseEntity.ok(categoryResp);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(cs.getAllCategories());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequestDto updateReq) {

        CategoryResponseDto updateResp = cs.updateCategory(id, updateReq);

//        if(updateResp == null) {
//            return ResponseEntity.notFound().build();
//        }

        return ResponseEntity.ok(updateResp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
         cs.deleteCategory(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/soft-delete/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {

        cs.softDelete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponseDto>> searchCategory(
            @RequestParam String name) {

        List<CategoryResponseDto> categories =
                cs.searchCategoryByName(name);

        return ResponseEntity.ok(categories);
    }

}
