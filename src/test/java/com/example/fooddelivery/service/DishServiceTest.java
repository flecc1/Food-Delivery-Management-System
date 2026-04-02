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
class DishServiceTest {
    @Mock
    private DishRepository dishRepository;
    @Mock
    private DishMapper dishMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private DishService dishService;

    @Test
    @DisplayName("findDishById: success")
    void findDishById_Success() {
        Long id = 1L;
        Dish dish = new Dish();
        DishDto dto = new DishDto();
        dto.setName("Pasta");

        when(dishRepository.findWithCategoryAndMenuById(id)).thenReturn(Optional.of(dish));
        when(dishMapper.toDto(dish)).thenReturn(dto);

        DishDto result = dishService.findDishById(id);

        assertThat(result.getName()).isEqualTo("Pasta");
        verify(dishRepository).findWithCategoryAndMenuById(id);
    }

    @Test
    @DisplayName("findDishById: not found throws exception")
    void findDishById_NotFound() {
        Long id = 1L;
        when(dishRepository.findWithCategoryAndMenuById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dishService.findDishById(id))
                .isInstanceOf(DishNotFoundException.class)
                .hasMessageContaining("Dish not found with id: " + id);
    }

    @Test
    @DisplayName("findDishByName: success")
    void findDishByName_Success() {
        String name = "Pizza";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Dish> page = new PageImpl<>(List.of(new Dish()));

        when(dishRepository.findDishByName(name, pageable)).thenReturn(page);
        when(dishMapper.toDto(any())).thenReturn(new DishDto());

        Page<DishDto> result = dishService.findDishByName(name, pageable);

        assertThat(result).isNotNull();
        verify(dishRepository).findDishByName(name, pageable);
    }

    @Test
    @DisplayName("findAllDishes: success")
    void findAllDishes_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Dish> page = new PageImpl<>(List.of(new Dish()));

        when(dishRepository.findAll(pageable)).thenReturn(page);
        when(dishMapper.toDto(any())).thenReturn(new DishDto());

        Page<DishDto> result = dishService.findAllDishes(pageable);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("findDishByPrice: success")
    void findDishByPrice_Success() {
        double price = 10.5;
        Pageable pageable = PageRequest.of(0, 10);
        Page<Dish> page = new PageImpl<>(List.of(new Dish()));

        when(dishRepository.findDishByPrice(price, pageable)).thenReturn(page);
        when(dishMapper.toDto(any())).thenReturn(new DishDto());

        Page<DishDto> result = dishService.findDishByPrice(price, pageable);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("addDish: success")
    void addDish_Success() {
        DishCreateDto createDto = new DishCreateDto();
        createDto.setCategoryId(1L);
        createDto.setMenuId(2L);

        Dish dish = new Dish();
        Category category = new Category();
        Menu menu = new Menu();

        when(dishMapper.toEntity(createDto)).thenReturn(dish);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(menuRepository.findById(2L)).thenReturn(Optional.of(menu));
        when(dishRepository.save(dish)).thenReturn(dish);
        when(dishMapper.toDto(dish)).thenReturn(new DishDto());

        DishDto result = dishService.addDish(createDto);

        assertThat(result).isNotNull();
        verify(dishRepository).save(dish);
    }

    @Test
    @DisplayName("addDish: category not found")
    void addDish_CategoryNotFound() {
        DishCreateDto createDto = new DishCreateDto();
        createDto.setCategoryId(1L);

        when(dishMapper.toEntity(createDto)).thenReturn(new Dish());
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dishService.addDish(createDto))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("addDish: menu not found")
    void addDish_MenuNotFound() {
        DishCreateDto createDto = new DishCreateDto();
        createDto.setCategoryId(1L);
        createDto.setMenuId(2L);

        when(dishMapper.toEntity(createDto)).thenReturn(new Dish());
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category()));
        when(menuRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dishService.addDish(createDto))
                .isInstanceOf(MenuNotFoundException.class);
    }

    @Test
    @DisplayName("updateDishById: success")
    void updateDishById_Success() {
        Long id = 1L;
        DishCreateDto updateDto = new DishCreateDto();
        updateDto.setCategoryId(10L);
        updateDto.setMenuId(20L);
        updateDto.setName("New Name");

        Dish existingDish = new Dish();
        Category category = new Category();
        Menu menu = new Menu();

        when(dishRepository.findWithCategoryAndMenuById(id)).thenReturn(Optional.of(existingDish));
        when(categoryRepository.findById(10L)).thenReturn(Optional.of(category));
        when(menuRepository.findById(20L)).thenReturn(Optional.of(menu));
        when(dishRepository.save(existingDish)).thenReturn(existingDish);
        when(dishMapper.toDto(existingDish)).thenReturn(new DishDto());

        DishDto result = dishService.updateDishById(id, updateDto);

        assertThat(result).isNotNull();
        assertThat(existingDish.getName()).isEqualTo("New Name");
    }

    @Test
    @DisplayName("updateDishById: dish not found")
    void updateDishById_DishNotFound() {
        when(dishRepository.findWithCategoryAndMenuById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> dishService.updateDishById(1L, new DishCreateDto()))
                .isInstanceOf(DishNotFoundException.class);
    }

    @Test
    @DisplayName("updateDishById: category not found")
    void updateDishById_CategoryNotFound() {
        DishCreateDto dto = new DishCreateDto();
        dto.setCategoryId(99L);
        when(dishRepository.findWithCategoryAndMenuById(1L)).thenReturn(Optional.of(new Dish()));
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dishService.updateDishById(1L, dto))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("updateDishById: menu not found")
    void updateDishById_MenuNotFound() {
        DishCreateDto dto = new DishCreateDto();
        dto.setCategoryId(1L);
        dto.setMenuId(88L);
        when(dishRepository.findWithCategoryAndMenuById(1L)).thenReturn(Optional.of(new Dish()));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category()));
        when(menuRepository.findById(88L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dishService.updateDishById(1L, dto))
                .isInstanceOf(MenuNotFoundException.class);
    }

    @Test
    @DisplayName("deleteDishById: success")
    void deleteDishById_Success() {
        Long id = 1L;
        when(dishRepository.existsById(id)).thenReturn(true);
        when(orderRepository.findByDishId(id)).thenReturn(Collections.emptyList());

        dishService.deleteDishById(id);

        verify(dishRepository).deleteById(id);
    }

    @Test
    @DisplayName("deleteDishById: dish not found")
    void deleteDishById_NotFound() {
        when(dishRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> dishService.deleteDishById(1L))
                .isInstanceOf(DishNotFoundException.class);
    }

    @Test
    @DisplayName("deleteDishById: linked to orders (Branch Coverage)")
    void deleteDishById_LinkedToOrders() {
        Long id = 1L;

        com.example.fooddelivery.entity.Order mockOrder = new com.example.fooddelivery.entity.Order();

        when(dishRepository.existsById(id)).thenReturn(true);

        when(orderRepository.findByDishId(id)).thenReturn(List.of(mockOrder));

        assertThatThrownBy(() -> dishService.deleteDishById(id))
                .isInstanceOf(OrderHasDishesException.class)
                .hasMessageContaining("has dishes and cannot be delete");

        verify(dishRepository, never()).deleteById(id);
    }
}