package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.dish.DishCreateDto;
import com.example.fooddelivery.dto.menu.MenuCreateDto;
import com.example.fooddelivery.dto.menu.MenuDto;
import com.example.fooddelivery.entity.Category;
import com.example.fooddelivery.entity.Dish;
import com.example.fooddelivery.entity.Menu;
import com.example.fooddelivery.entity.Restaurant;
import com.example.fooddelivery.exception.*;
import com.example.fooddelivery.mapper.DishMapper;
import com.example.fooddelivery.mapper.MenuMapper;
import com.example.fooddelivery.repository.CategoryRepository;
import com.example.fooddelivery.repository.DishRepository;
import com.example.fooddelivery.repository.MenuRepository;
import com.example.fooddelivery.repository.OrderRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock private MenuRepository menuRepository;
    @Mock private MenuMapper menuMapper;
    @Mock private RestaurantRepository restaurantRepository;
    @Mock private DishRepository dishRepository;
    @Mock private OrderRepository orderRepository;
    @Mock private DishMapper dishMapper;
    @Mock private CategoryRepository categoryRepository;

    @InjectMocks
    private MenuService menuService;

    private Menu menu;
    private MenuDto menuDto;
    private Restaurant restaurant;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");

        menu = new Menu();
        menu.setId(1L);
        menu.setName("Test Menu");
        menu.setRestaurant(restaurant);
        menu.setDishes(new ArrayList<>());

        menuDto = new MenuDto();
        menuDto.setId(1L);
        menuDto.setName("Test Menu DTO");

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void findById_Success() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(menuMapper.toDto(menu)).thenReturn(menuDto);

        MenuDto result = menuService.findById(1L);

        assertEquals(menuDto, result);
        verify(menuRepository).findById(1L);
    }

    @Test
    void findById_MenuNotFoundException() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MenuNotFoundException.class, () -> menuService.findById(1L));
    }

    @Test
    void findAllMenus_Success() {
        Page<Menu> menuPage = new PageImpl<>(List.of(menu));
        when(menuRepository.findAll(pageable)).thenReturn(menuPage);
        when(menuMapper.toDto(menu)).thenReturn(menuDto);

        Page<MenuDto> result = menuService.findAllMenus(pageable);

        assertEquals(1, result.getTotalElements());
        verify(menuRepository).findAll(pageable);
    }

    @Test
    void findMenuByName_Success() {
        Page<Menu> menuPage = new PageImpl<>(List.of(menu));
        when(menuRepository.findMenuByName("Test", pageable)).thenReturn(menuPage);
        when(menuMapper.toDto(menu)).thenReturn(menuDto);

        Page<MenuDto> result = menuService.findMenuByName("Test", pageable);

        assertEquals(1, result.getTotalElements());
    }

    // --- findMenuByRestaurantId ---

    @Test
    void findMenuByRestaurantId_Success() {
        when(restaurantRepository.existsById(1L)).thenReturn(true);
        Page<Menu> menuPage = new PageImpl<>(List.of(menu));
        when(menuRepository.findAllByRestaurantId(1L, pageable)).thenReturn(menuPage);
        when(menuMapper.toDto(menu)).thenReturn(menuDto);

        Page<MenuDto> result = menuService.findMenuByRestaurantId(1L, pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findMenuByRestaurantId_RestaurantNotFoundException() {
        when(restaurantRepository.existsById(1L)).thenReturn(false);

        assertThrows(RestaurantNotFoundException.class, () -> menuService.findMenuByRestaurantId(1L, pageable));
    }

    @Test
    void addMenu_Success_WithoutDishes() {
        MenuCreateDto dto = new MenuCreateDto();
        dto.setName("New Menu");
        dto.setRestaurantId(1L);
        dto.setDishesIds(null);

        when(menuMapper.toEntity(dto)).thenReturn(menu);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(menuRepository.save(menu)).thenReturn(menu);
        when(menuMapper.toDto(menu)).thenReturn(menuDto);

        MenuDto result = menuService.addMenu(dto);

        assertEquals(menuDto, result);
        assertTrue(menu.isActive());
        verify(menuRepository).save(menu);
    }

    @Test
    void addMenu_Success_WithDishes() {
        MenuCreateDto dto = new MenuCreateDto();
        dto.setRestaurantId(1L);
        dto.setDishesIds(List.of(1L));

        Dish dish = new Dish();
        dish.setId(1L);
        Menu otherMenu = new Menu();
        otherMenu.setRestaurant(restaurant);
        dish.setMenu(otherMenu);

        when(menuMapper.toEntity(dto)).thenReturn(menu);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(dishRepository.findAllById(dto.getDishesIds())).thenReturn(List.of(dish));
        when(menuRepository.save(menu)).thenReturn(menu);
        when(menuMapper.toDto(menu)).thenReturn(menuDto);

        MenuDto result = menuService.addMenu(dto);

        assertEquals(menuDto, result);
        assertEquals(menu, dish.getMenu());
    }

    @Test
    void addMenu_RestaurantNotFound() {
        MenuCreateDto dto = new MenuCreateDto();
        dto.setRestaurantId(1L);
        when(menuMapper.toEntity(dto)).thenReturn(menu);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> menuService.addMenu(dto));
    }

    @Test
    void addMenu_DishRestaurantMismatch() {
        MenuCreateDto dto = new MenuCreateDto();
        dto.setRestaurantId(1L);
        dto.setDishesIds(List.of(1L));

        Restaurant otherRestaurant = new Restaurant();
        otherRestaurant.setId(2L);
        Menu otherMenu = new Menu();
        otherMenu.setRestaurant(otherRestaurant);

        Dish dish = new Dish();
        dish.setMenu(otherMenu);

        when(menuMapper.toEntity(dto)).thenReturn(menu);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(dishRepository.findAllById(dto.getDishesIds())).thenReturn(List.of(dish));

        assertThrows(IllegalArgumentException.class, () -> menuService.addMenu(dto));
    }

    @Test
    void updateMenuById_Success_SameRestaurant_NoDishes() {
        MenuCreateDto dto = new MenuCreateDto();
        dto.setName("Updated Name");
        dto.setDescription("Desc");
        dto.setRestaurantId(1L);
        dto.setDishesIds(null);

        when(menuRepository.findWithRestaurantAndDishesById(1L)).thenReturn(Optional.of(menu));
        when(menuRepository.save(menu)).thenReturn(menu);
        when(menuMapper.toDto(menu)).thenReturn(menuDto);

        MenuDto result = menuService.updateMenuById(1L, dto);

        assertEquals(menuDto, result);
        assertEquals("Updated Name", menu.getName());
        verify(restaurantRepository, never()).findById(any());
    }

    @Test
    void updateMenuById_Success_DifferentRestaurant_WithDishes() {
        MenuCreateDto dto = new MenuCreateDto();
        dto.setRestaurantId(2L);
        dto.setDishesIds(List.of(1L));

        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setId(2L);

        Dish newDish = new Dish();

        when(menuRepository.findWithRestaurantAndDishesById(1L)).thenReturn(Optional.of(menu));
        when(restaurantRepository.findById(2L)).thenReturn(Optional.of(newRestaurant));
        when(dishRepository.findAllById(dto.getDishesIds())).thenReturn(List.of(newDish));
        when(menuRepository.save(menu)).thenReturn(menu);
        when(menuMapper.toDto(menu)).thenReturn(menuDto);

        MenuDto result = menuService.updateMenuById(1L, dto);

        assertEquals(menuDto, result);
        assertEquals(newRestaurant, menu.getRestaurant());
        assertTrue(menu.getDishes().contains(newDish));
    }

    @Test
    void updateMenuById_MenuNotFound() {
        MenuCreateDto dto = new MenuCreateDto();
        when(menuRepository.findWithRestaurantAndDishesById(1L)).thenReturn(Optional.empty());

        assertThrows(MenuNotFoundException.class, () -> menuService.updateMenuById(1L, dto));
    }

    @Test
    void updateMenuById_NewRestaurantNotFound() {
        MenuCreateDto dto = new MenuCreateDto();
        dto.setRestaurantId(2L);

        when(menuRepository.findWithRestaurantAndDishesById(1L)).thenReturn(Optional.of(menu));
        when(restaurantRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> menuService.updateMenuById(1L, dto));
    }

    @Test
    void addDishToMenu_Success() {
        Dish dish = new Dish();
        dish.setId(1L);
        Menu otherMenu = new Menu();
        otherMenu.setRestaurant(restaurant);
        dish.setMenu(otherMenu);

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(menuMapper.toDto(menu)).thenReturn(menuDto);

        MenuDto result = menuService.addDishToMenu(1L, 1L);

        assertEquals(menuDto, result);
        assertEquals(menu, dish.getMenu());
        verify(dishRepository).save(dish);
    }

    @Test
    void addDishToMenu_MenuNotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MenuNotFoundException.class, () -> menuService.addDishToMenu(1L, 1L));
    }

    @Test
    void addDishToMenu_DishNotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DishNotFoundException.class, () -> menuService.addDishToMenu(1L, 1L));
    }

    @Test
    void addDishToMenu_MismatchRestaurant() {
        Dish dish = new Dish();
        Restaurant otherRestaurant = new Restaurant();
        otherRestaurant.setId(2L);
        otherRestaurant.setName("Other");
        Menu otherMenu = new Menu();
        otherMenu.setRestaurant(otherRestaurant);
        dish.setMenu(otherMenu);

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));

        assertThrows(DishHasAnotherRestaurantException.class, () -> menuService.addDishToMenu(1L, 1L));
    }

    @Test
    void deleteMenuById_Success() {
        when(menuRepository.existsById(1L)).thenReturn(true);
        when(orderRepository.findByMenuId(1L)).thenReturn(List.of());

        menuService.deleteMenuById(1L);

        verify(menuRepository).deleteById(1L);
    }

    @Test
    void deleteMenuById_NotFound() {
        when(menuRepository.existsById(1L)).thenReturn(false);

        assertThrows(MenuNotFoundException.class, () -> menuService.deleteMenuById(1L));
    }

    @Test
    void deleteMenuById_HasOrders() {
        when(menuRepository.existsById(1L)).thenReturn(true);

        com.example.fooddelivery.entity.Order dummyOrder = new com.example.fooddelivery.entity.Order();
        when(orderRepository.findByMenuId(1L)).thenReturn(List.of(dummyOrder));

        assertThrows(MenuHasDishesException.class, () -> menuService.deleteMenuById(1L));
    }

    @Test
    void addDishesToMenu_Success_WithCategory() {
        DishCreateDto dishDto = new DishCreateDto();
        dishDto.setMenuId(1L);
        dishDto.setCategoryId(1L);

        Dish dish = new Dish();
        Category category = new Category();

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(dishMapper.toEntity(dishDto)).thenReturn(dish);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(dishRepository.saveAndFlush(dish)).thenReturn(dish);
        when(menuMapper.toDto(menu)).thenReturn(menuDto);

        MenuDto result = menuService.addDishesToMenu(1L, List.of(dishDto));

        assertEquals(menuDto, result);
        assertEquals(menu, dish.getMenu());
        assertEquals(category, dish.getCategory());
        assertTrue(menu.getDishes().contains(dish));
    }

    @Test
    void addDishesToMenu_Success_WithoutCategory() {
        DishCreateDto dishDto = new DishCreateDto();
        dishDto.setMenuId(1L);
        dishDto.setCategoryId(null);

        Dish dish = new Dish();

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(dishMapper.toEntity(dishDto)).thenReturn(dish);
        when(dishRepository.saveAndFlush(dish)).thenReturn(dish);
        when(menuMapper.toDto(menu)).thenReturn(menuDto);

        MenuDto result = menuService.addDishesToMenu(1L, List.of(dishDto));

        assertEquals(menuDto, result);
        assertEquals(menu, dish.getMenu());
        assertNull(dish.getCategory());
    }

    @Test
    void addDishesToMenu_MenuNotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MenuNotFoundException.class, () -> menuService.addDishesToMenu(1L, List.of()));
    }

    @Test
    void addDishesToMenu_WrongMenuId() {
        DishCreateDto dishDto = new DishCreateDto();
        dishDto.setMenuId(2L);

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        assertThrows(DishHasAnotherRestaurantException.class, () -> menuService.addDishesToMenu(1L, List.of(dishDto)));
    }

    @Test
    void addDishesToMenu_CategoryNotFound() {
        DishCreateDto dishDto = new DishCreateDto();
        dishDto.setMenuId(1L);
        dishDto.setCategoryId(1L);

        Dish dish = new Dish();

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(dishMapper.toEntity(dishDto)).thenReturn(dish);
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> menuService.addDishesToMenu(1L, List.of(dishDto)));
    }
}