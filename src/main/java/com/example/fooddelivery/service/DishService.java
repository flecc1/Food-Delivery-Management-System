package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.dish.DishCreateDto;
import com.example.fooddelivery.dto.dish.DishDto;
import com.example.fooddelivery.entity.Category;
import com.example.fooddelivery.entity.Dish;
import com.example.fooddelivery.entity.Menu;
import com.example.fooddelivery.exception.CategoryNotFoundException;
import com.example.fooddelivery.exception.DishNotFoundException;
import com.example.fooddelivery.exception.MenuNotFoundException;
import com.example.fooddelivery.exception.OrderHasDishesException;
import com.example.fooddelivery.mapper.DishMapper;
import com.example.fooddelivery.repository.CategoryRepository;
import com.example.fooddelivery.repository.DishRepository;
import com.example.fooddelivery.repository.MenuRepository;
import com.example.fooddelivery.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DishService {
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    public DishDto findDishById(Long id) {
        log.debug("attempting to find dish with id: {}", id);
        DishDto dto = dishRepository.findWithCategoryAndMenuById(id)
                .map(dishMapper::toDto)
                .orElseThrow(() -> new DishNotFoundException("Dish not found with id: " + id));
        log.info("found dish: {} id: {} successfully", dto.getName(), id);
        return dto;
    }

    public Page<DishDto> findDishByName(String name, Pageable pageable) {
        log.debug("try find dishes by name: '{}'", name);
        Page<DishDto> page = dishRepository.findDishByName(name, pageable).map(dishMapper::toDto);
        log.info("found dishes successfully with name: {}", name);
        return page;
    }

    public Page<DishDto> findAllDishes(Pageable pageable) {
        log.debug("try find all dishes");
        Page<DishDto> page = dishRepository.findAll(pageable).map(dishMapper::toDto);
        log.info("dishes found successfully");
        return page;
    }

    public Page<DishDto> findDishByPrice(double price, Pageable pageable) {
        log.debug("try find dishes by price: {}", price);
        Page<DishDto> page = dishRepository
                .findDishByPrice(price, pageable)
                .map(dishMapper::toDto);
        log.info("dishes with price: {} found successfully", price);
        return page;
    }

    @Transactional
    public DishDto addDish(DishCreateDto dishCreateDto) {
        log.debug("try add new dish: {} for category ID: {} and menu id: {}",
                dishCreateDto.getName(), dishCreateDto.getCategoryId(), dishCreateDto.getMenuId());
        Dish dish = dishMapper.toEntity(dishCreateDto);
        Category category = categoryRepository.findById(dishCreateDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        Menu menu = menuRepository.findById(dishCreateDto.getMenuId())
                .orElseThrow(() -> new MenuNotFoundException("Menu not found with id: "
                        + dishCreateDto.getMenuId()));
        dish.setMenu(menu);
        dish.setCategory(category);
        Dish save = dishRepository.save(dish);
        log.info("dish save successfully: {}", save.getName());
        return dishMapper.toDto(save);
    }

    @Transactional
    public DishDto updateDishById(Long id, DishCreateDto dishCreateDto) {
        log.debug("try update dish with id: {}, and name: {}", id, dishCreateDto.getName());
        Dish exist = dishRepository.findWithCategoryAndMenuById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish not found with id: " + id));

        exist.setName(dishCreateDto.getName());
        exist.setPrice(dishCreateDto.getPrice());
        exist.setDescription(dishCreateDto.getDescription());

        Category category = categoryRepository.findById(dishCreateDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: "
                        + dishCreateDto.getCategoryId()));
        exist.setCategory(category);

        Menu menu = menuRepository.findById(dishCreateDto.getMenuId())
                .orElseThrow(() -> new MenuNotFoundException("Menu not found with id: "
                        + dishCreateDto.getMenuId()));
        exist.setMenu(menu);
        Dish save = dishRepository.save(exist);
        log.info("dish: {} update successfully", save.getName());
        return dishMapper.toDto(save);
    }

    @Transactional
    public void deleteDishById(Long id) {
        log.debug("try delete dish with id: {}", id);
        if (!dishRepository.existsById(id)) {
            throw new DishNotFoundException("Dish not found with id: " + id);
        }
        log.debug("check if dish id: {} is part of orders", id);
        if (!orderRepository.findByDishId(id).isEmpty()) {
            log.warn("delete failed: dish id {} is linked to existing orders", id);
            throw new OrderHasDishesException("Order with id " + id + "has dishes and cannot be delete");
        }
        dishRepository.deleteById(id);
        log.info("dish delete successfully with id: {}", id);
    }
}
