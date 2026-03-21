package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.category.CategoryCreateDto;
import com.example.fooddelivery.dto.category.CategoryDto;
import com.example.fooddelivery.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public Page<CategoryDto> getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name) {
        Pageable pageable = PageRequest.of(page, size);
        if (name != null) {
            return categoryService.findCategoryByName(name, pageable);
        }
        return categoryService.getAllCategories(pageable);
    }

    @GetMapping("/{id:\\d+}")
    public CategoryDto getCategoriesById(@PathVariable Long id) {
        return categoryService.findCategoryById(id);
    }

    @PostMapping
    public CategoryDto createCategory(@Valid @RequestBody CategoryCreateDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @PostMapping("/test-transaction")
    public String testTransaction(@RequestParam String name) {
        categoryService.saveMultipleCategoriesWithStepback(name);
        return "Запрос выполнен успешно (без ошибок)";
    }

    @PutMapping("/{id:\\d+}")
    public CategoryDto updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryCreateDto categoryCreateDto) {
        return categoryService.updateCategoryById(id, categoryCreateDto);
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }

}
