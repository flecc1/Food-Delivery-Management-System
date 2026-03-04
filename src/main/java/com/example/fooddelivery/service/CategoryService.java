package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.category.CategoryCreateDto;
import com.example.fooddelivery.dto.category.CategoryDto;
import com.example.fooddelivery.entity.Category;
import com.example.fooddelivery.exception.CategoryNotFoundException;
import com.example.fooddelivery.mapper.CategoryMapper;
import com.example.fooddelivery.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDto findCategoryById(Long id) {
        return categoryRepository.findWithDishesById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    public CategoryDto findBadById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }

    public List<CategoryDto> findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name).stream().map(categoryMapper::toDto).toList();
    }

    @Transactional
    public CategoryDto addCategory(CategoryCreateDto categoryCreateDto) {
        Category category = categoryMapper.toEntity(categoryCreateDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Cant delete: category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Transactional
    public CategoryDto updateCategoryById(Long id, CategoryCreateDto categoryCreateDto) {
        Category save = categoryRepository.findWithDishesById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        save.setName(categoryCreateDto.getName());
        return categoryMapper.toDto(categoryRepository.save(save));
    }

    @Transactional
    public void saveMultipleCategoriesWithStepback(String name) {
        Category cat1 = new Category();
        cat1.setName(name);
        categoryRepository.save(cat1);
        System.out.println(">>> Первая категория '" + name + "' сохранена в БД");

        if (name.equalsIgnoreCase("error")) {
            System.out.println(">>> Произошла ошибка! Выбрасываем исключение...");
            throw new RuntimeException("Демонстрационная ошибка транзакции");
        }

        Category cat2 = new Category();
        cat2.setName(name + "_second");
        categoryRepository.save(cat2);
        System.out.println(">>> Вторая категория сохранена успешно");
    }
}
