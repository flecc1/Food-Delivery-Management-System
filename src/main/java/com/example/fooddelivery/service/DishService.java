package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.dish.DishCreateDto;
import com.example.fooddelivery.dto.dish.DishDto;
import com.example.fooddelivery.entity.Category;
import com.example.fooddelivery.entity.Dish;
import com.example.fooddelivery.exception.CategoryNotFoundException;
import com.example.fooddelivery.exception.DishNotFoundException;
import com.example.fooddelivery.mapper.DishMapper;
import com.example.fooddelivery.repository.CategoryRepository;
import com.example.fooddelivery.repository.DishRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final CategoryRepository categoryRepository;

    public DishDto findDishById(Long id) {
        return dishRepository.findById(id).map(dishMapper::toDto)
                .orElseThrow(() -> new DishNotFoundException("Dish not found with id: " + id));
    }

    public List<DishDto> findDishByName(String name) {
        return dishRepository.findDishByName(name)
                .stream().map(dishMapper::toDto).toList();
    }

    public List<DishDto> findAllDishes() {
        return dishRepository.findAll().stream().map(dishMapper::toDto).toList();
    }

    public List<DishDto> findDishByPrice(double price) {
        return dishRepository.findDishByPrice(price)
                .stream().map(dishMapper::toDto).toList();
    }

    @Transactional
    public DishDto addDish(DishCreateDto dishCreateDto) {
        Dish dish = dishMapper.toEntity(dishCreateDto);
        Category category = categoryRepository.findById(dishCreateDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + dishCreateDto.getCategoryId()));
        dish.setCategory(category);
        return dishMapper.toDto(dishRepository.save(dish));
    }

    @Transactional
    public DishDto updateDishById(Long id, DishCreateDto dishCreateDto) {
        Dish exist = dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish not found with id: " + id));
        exist.setName(dishCreateDto.getName());
        exist.setPrice(dishCreateDto.getPrice());
        exist.setDescription(dishCreateDto.getDescription());
        Category category = categoryRepository.findById(dishCreateDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + dishCreateDto.getCategoryId()));
        exist.setCategory(category);
        return dishMapper.toDto(dishRepository.save(exist));
    }

    @Transactional
    public void deleteDishById(Long id) {
        if(!dishRepository.existsById(id)) {
            throw  new DishNotFoundException("Dish not found with id: " + id);
        }
        dishRepository.deleteById(id);
    }
}
