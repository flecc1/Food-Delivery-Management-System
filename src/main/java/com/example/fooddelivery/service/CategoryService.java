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
        log.debug("try to find category with id: {}", id);
        CategoryDto dto = categoryRepository.findWithDishesById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND_MSG + id));
        log.info("found category: with name: {}, id: {} successfully", dto.getName(), id);
        return dto;
    }

    public Page<CategoryDto> getAllCategories(Pageable pageable) {
        log.debug("try to find all categories");
        Page<CategoryDto> categoryPage = categoryRepository.findAll(pageable)
                .map(categoryMapper::toDto);
        log.info("found all categories successfully");
        return categoryPage;
    }

    public Page<CategoryDto> findCategoryByName(String name, Pageable pageable) {
        log.debug("try to find category with name: {}", name);
        Page<CategoryDto> categoryPage = categoryRepository.findCategoryByName(name, pageable)
                .map(categoryMapper::toDto);
        log.info("found category: with name: {} successfully", name);
        return categoryPage;
    }

    @Transactional
    public CategoryDto addCategory(CategoryCreateDto categoryCreateDto) {
        log.debug("try to add category: {}", categoryCreateDto.getName());
        Category category = categoryMapper.toEntity(categoryCreateDto);
        Category save = categoryRepository.save(category);
        log.info("save category: with name: {}, id: {} successfully", save.getName(), save.getId());
        return categoryMapper.toDto(save);
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        log.debug("try to delete category with id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND_MSG + id));
        log.debug("checking dishes for category with id: {}", id);
        if (!category.getDishes().isEmpty()) {
            log.warn("delete failed found dishes for category with id: {}", id);
            throw new CategoryHasDishesException("category with id: " + id + " has dishes cannot be deleted");
        }
        categoryRepository.deleteById(id);
        log.info("delete category: with name: {}, id: {} successfully", category.getName(), id);
    }

    @Transactional
    public CategoryDto updateCategoryById(Long id, CategoryCreateDto categoryCreateDto) {
        log.debug("try to update category with id: {}, name: {}", id, categoryCreateDto.getName());
        Category save = categoryRepository.findWithDishesById(id)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND_MSG + id));
        save.setName(categoryCreateDto.getName());
        log.info("update category: with name: {}, id: {} successfully", save.getName(), id);
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
