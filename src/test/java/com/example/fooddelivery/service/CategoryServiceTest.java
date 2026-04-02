package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.category.CategoryCreateDto;
import com.example.fooddelivery.dto.category.CategoryDto;
import com.example.fooddelivery.entity.Category;
import com.example.fooddelivery.entity.Dish;
import com.example.fooddelivery.exception.CategoryHasDishesException;
import com.example.fooddelivery.exception.CategoryNotFoundException;
import com.example.fooddelivery.exception.TransactionTestException;
import com.example.fooddelivery.mapper.CategoryMapper;
import com.example.fooddelivery.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("findCategoryById: success")
    void findCategoryById_ShouldReturnDto_WhenFound() {
        Long id = 1L;
        Category category = new Category();
        CategoryDto dto = new CategoryDto();

        when(categoryRepository.findWithDishesById(id)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(dto);

        CategoryDto result = categoryService.findCategoryById(id);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    @DisplayName("findCategoryById: not found throws exception")
    void findCategoryById_ShouldThrowException_WhenNotFound() {
        Long id = 1L;
        when(categoryRepository.findWithDishesById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.findCategoryById(id))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessageContaining("Category not found with id: " + id);
    }

    @Test
    @DisplayName("getAllCategories: success")
    void getAllCategories_ShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Category category = new Category();
        Page<Category> categoryPage = new PageImpl<>(List.of(category));

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(any(Category.class))).thenReturn(new CategoryDto());

        Page<CategoryDto> result = categoryService.getAllCategories(pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(categoryRepository).findAll(pageable);
    }

    @Test
    @DisplayName("findCategoryByName: success")
    void findCategoryByName_ShouldReturnPage() {
        String name = "Pizza";
        Pageable pageable = PageRequest.of(0, 10);
        Category category = new Category();
        Page<Category> categoryPage = new PageImpl<>(List.of(category));

        when(categoryRepository.findCategoryByName(name, pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(any(Category.class))).thenReturn(new CategoryDto());

        Page<CategoryDto> result = categoryService.findCategoryByName(name, pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("addCategory: success")
    void addCategory_ShouldReturnSavedDto() {
        CategoryCreateDto createDto = new CategoryCreateDto();
        createDto.setName("Burgers");
        Category category = new Category();
        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Burgers");

        when(categoryMapper.toEntity(createDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(categoryMapper.toDto(savedCategory)).thenReturn(new CategoryDto());

        CategoryDto result = categoryService.addCategory(createDto);

        assertThat(result).isNotNull();
        verify(categoryRepository).save(category);
    }

    @Test
    @DisplayName("deleteCategoryById: success when no dishes")
    void deleteCategoryById_ShouldDelete_WhenNoDishes() {
        Long id = 1L;
        Category category = new Category();
        category.setDishes(Collections.emptyList());

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        categoryService.deleteCategoryById(id);

        verify(categoryRepository).deleteById(id);
    }

    @Test
    @DisplayName("deleteCategoryById: throws exception when has dishes (Branch Coverage)")
    void deleteCategoryById_ShouldThrowException_WhenHasDishes() {
        Long id = 1L;
        Category category = new Category();
        category.setDishes(List.of(new Dish()));

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        assertThatThrownBy(() -> categoryService.deleteCategoryById(id))
                .isInstanceOf(CategoryHasDishesException.class)
                .hasMessageContaining("has dishes cannot be deleted");

        verify(categoryRepository, never()).deleteById(id);
    }

    @Test
    @DisplayName("deleteCategoryById: not found")
    void deleteCategoryById_ShouldThrowException_WhenNotFound() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.deleteCategoryById(id))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("updateCategoryById: success")
    void updateCategoryById_ShouldUpdateAndReturnDto() {
        Long id = 1L;
        CategoryCreateDto createDto = new CategoryCreateDto();
        createDto.setName("New Name");
        Category existingCategory = new Category();

        when(categoryRepository.findWithDishesById(id)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);
        when(categoryMapper.toDto(any(Category.class))).thenReturn(new CategoryDto());

        CategoryDto result = categoryService.updateCategoryById(id, createDto);

        assertThat(result).isNotNull();
        assertThat(existingCategory.getName()).isEqualTo("New Name");
    }

    @Test
    @DisplayName("updateCategoryById: not found")
    void updateCategoryById_ShouldThrowException_WhenNotFound() {
        Long id = 1L;
        when(categoryRepository.findWithDishesById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategoryById(id, new CategoryCreateDto()))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("saveMultipleCategories: success (name != error)")
    void saveMultipleCategories_ShouldSaveTwoCategories_WhenNameIsNotError() {
        String name = "ValidName";

        categoryService.saveMultipleCategoriesWithStepback(name);

        verify(categoryRepository, times(2)).save(any(Category.class));
    }

    @Test
    @DisplayName("saveMultipleCategories: throws exception (Branch Coverage 'error')")
    void saveMultipleCategories_ShouldThrowException_WhenNameIsError() {
        String name = "error";

        assertThatThrownBy(() -> categoryService.saveMultipleCategoriesWithStepback(name))
                .isInstanceOf(TransactionTestException.class)
                .hasMessage("Демонстрационная ошибка транзакции");

        verify(categoryRepository, times(1)).save(any(Category.class));
    }
}