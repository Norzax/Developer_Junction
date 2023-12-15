package com.example.day_3_source.controller.admin;

import com.example.day_3_source.model.dto.CategoryDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.services.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryControllerAdmin {
    private final CategoryServiceImpl categoryServiceImpl;

    @Autowired
    public CategoryControllerAdmin(CategoryServiceImpl categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @GetMapping("/listCategory")
    public ResponseEntity<BaseResponseDto<List<CategoryDto>>> getAll() {
        return ResponseEntity.ok(categoryServiceImpl.getAll());
    }

    @GetMapping("/findCategory/{id}")
    public ResponseEntity<BaseResponseDto<CategoryDto>> getById(@PathVariable("id") Long categoryId) {
        return ResponseEntity.ok(categoryServiceImpl.getById(categoryId));
    }

    @PostMapping("/createCategory")
    public ResponseEntity<BaseResponseDto<CategoryDto>> create(@RequestBody() CategoryDto category) {
        return ResponseEntity.ok(categoryServiceImpl.create(category));
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<BaseResponseDto<CategoryDto>> update(@PathVariable("id") Long categoryId, @RequestBody() CategoryDto category) {
        return ResponseEntity.ok(categoryServiceImpl.update(categoryId, category));
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<BaseResponseDto<Void>> delete(@PathVariable("id") Long categoryId) {
        return ResponseEntity.ok(categoryServiceImpl.delete(categoryId));
    }

    @DeleteMapping("/deleteCategories")
    public ResponseEntity<BaseResponseDto<Void>> deleteCategories(@RequestParam List<Long> categoryIds) {
        return ResponseEntity.ok(categoryServiceImpl.deleteMultipleCategories(categoryIds));
    }
}
