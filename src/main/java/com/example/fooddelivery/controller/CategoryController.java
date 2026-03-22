package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.category.CategoryCreateDto;
import com.example.fooddelivery.dto.category.CategoryDto;
import com.example.fooddelivery.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Tag(name = "Категории", description = "Управление категориями блюд")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Получить все категории",
            description = "Возвращает список категорий с поддержкой пагинации и поиска по имени")
    public Page<CategoryDto> getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name) {
        log.info("request to get categories with page {} and size {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        if (name != null) {
            return categoryService.findCategoryByName(name, pageable);
        }
        return categoryService.getAllCategories(pageable);
    }

    @GetMapping("/{id:\\d+}")
    @Operation(summary = "Получить категорию по ID")
    public CategoryDto getCategoriesById(@PathVariable Long id) {
        log.info("requests for getting category by id: {}", id);
        return categoryService.findCategoryById(id);
    }

    @PostMapping
    @Operation(summary = "Создать новую категорию")
    public CategoryDto createCategory(@Valid @RequestBody CategoryCreateDto categoryDto) {
        log.info("request to create category : {}", categoryDto.getName());
        return categoryService.addCategory(categoryDto);
    }

    @PostMapping("/test-transaction")
    @Operation(summary = "Тест транзакций", description = "Служебный эндпоинт для проверки отката транзакций")
    public String testTransaction(@RequestParam String name) {
        log.info("request to test transaction : {}", name);
        categoryService.saveMultipleCategoriesWithStepback(name);
        return "Запрос выполнен успешно (без ошибок)";
    }

    @PutMapping("/{id:\\d+}")
    @Operation(summary = "Обновить категорию")
    public CategoryDto updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryCreateDto categoryCreateDto) {
        log.info("request to update category : {} with id {}", categoryCreateDto.getName(), id);
        return categoryService.updateCategoryById(id, categoryCreateDto);
    }

    @DeleteMapping("/{id:\\d+}")
    @Operation(summary = "Удалить категорию")
    public void deleteCategory(@PathVariable Long id) {
        log.info("request to delete category : with id {}", id);
        categoryService.deleteCategoryById(id);
    }

}
