package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.category.CategoryCreateDto;
import com.example.fooddelivery.dto.category.CategoryDto;
import com.example.fooddelivery.entity.Category;
import com.example.fooddelivery.exception.CategoryHasDishesException;
import com.example.fooddelivery.exception.CategoryNotFoundException;
import com.example.fooddelivery.exception.TransactionTestException;
import com.example.fooddelivery.mapper.CategoryMapper;
import com.example.fooddelivery.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private static final String CATEGORY_NOT_FOUND_MSG = "Category not found with id: ";

    public CategoryDto findCategoryById(Long id) {
        return categoryRepository.findWithDishesById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND_MSG + id));
    }

    public Page<CategoryDto> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::toDto);
    }

    public Page<CategoryDto> findCategoryByName(String name, Pageable pageable) {
        return categoryRepository.findCategoryByName(name, pageable).map(categoryMapper::toDto);
    }

    @Transactional
    public CategoryDto addCategory(CategoryCreateDto categoryCreateDto) {
        Category category = categoryMapper.toEntity(categoryCreateDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND_MSG + id));
        if (!category.getDishes().isEmpty()) {
            throw new CategoryHasDishesException("category with id: " + id + " has dishes cannot be deleted");
        }
        categoryRepository.deleteById(id);
    }

    @Transactional
    public CategoryDto updateCategoryById(Long id, CategoryCreateDto categoryCreateDto) {
        Category save = categoryRepository.findWithDishesById(id)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND_MSG + id));
        save.setName(categoryCreateDto.getName());
        return categoryMapper.toDto(categoryRepository.save(save));
    }

    @Transactional
    public void saveMultipleCategoriesWithStepback(String name) {
        Category cat1 = new Category();
        cat1.setName(name);
        categoryRepository.save(cat1);
        if (name.equalsIgnoreCase("error")) {
            throw new TransactionTestException("Демонстрационная ошибка транзакции");
        }

        Category cat2 = new Category();
        cat2.setName(name + "_second");
        categoryRepository.save(cat2);
    }
}
