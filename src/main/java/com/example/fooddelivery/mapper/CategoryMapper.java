package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.category.CategoryCreateDto;
import com.example.fooddelivery.dto.category.CategoryDto;
import com.example.fooddelivery.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDto toDto(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        if (category.getDishes() != null) {
            categoryDto.setDishCount(category.getDishes().size());
        } else {
            categoryDto.setDishCount(0);
        }
        return categoryDto;
    }

    public Category toEntity(CategoryCreateDto categoryCreateDto) {
        if (categoryCreateDto == null) {
            return null;
        }
        Category category = new Category();
        category.setName(categoryCreateDto.getName());
        return category;
    }
}
