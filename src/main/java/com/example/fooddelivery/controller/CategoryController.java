package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.category.CategoryCreateDto;
import com.example.fooddelivery.dto.category.CategoryDto;
import com.example.fooddelivery.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(required = false) String name) {
        if (name != null) {
            return categoryService.findCategoryByName(name);
        }
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id:\\d+}")
    public CategoryDto getCategoriesById(@PathVariable Long id) {
        return categoryService.findCategoryById(id);
    }

    @GetMapping("/bad/{id:\\d+}")
    public CategoryDto getCategoryByIdBad(@PathVariable Long id) {
        return categoryService.findBadById(id);
    }

    @PostMapping
    public CategoryDto createCategory(@RequestBody CategoryCreateDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @PutMapping("/{id:\\d+}")
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody CategoryCreateDto categoryCreateDto) {
        return categoryService.updateCategoryById(id, categoryCreateDto);
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }

}
