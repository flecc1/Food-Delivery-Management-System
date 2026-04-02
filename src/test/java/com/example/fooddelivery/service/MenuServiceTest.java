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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    @DisplayName("findById: success")
    void findById_Success() {
        Menu menu = new Menu();
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(menuMapper.toDto(menu)).thenReturn(new MenuDto());

        assertThat(menuService.findById(1L)).isNotNull();
    }

    @Test
    @DisplayName("findById: not found")
    void findById_NotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> menuService.findById(1L))
                .isInstanceOf(MenuNotFoundException.class);
    }

    @Test
    @DisplayName("findAllMenus: success")
    void findAllMenus_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        when(menuRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(new Menu())));
        when(menuMapper.toDto(any())).thenReturn(new MenuDto());

        Page<MenuDto> result = menuService.findAllMenus(pageable);
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("findMenuByName: success")
    void findMenuByName_Success() {
        String name = "Lunch";
        Pageable pageable = PageRequest.of(0, 10);
        when(menuRepository.findMenuByName(name, pageable)).thenReturn(new PageImpl<>(List.of(new Menu())));
        when(menuMapper.toDto(any())).thenReturn(new MenuDto());

        assertThat(menuService.findMenuByName(name, pageable)).hasSize(1);
    }

    @Test
    @DisplayName("findMenuByRestaurantId: success")
    void findMenuByRestaurantId_Success() {
        Long resId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        when(restaurantRepository.existsById(resId)).thenReturn(true);
        when(menuRepository.findAllByRestaurantId(resId, pageable)).thenReturn(new PageImpl<>(List.of(new Menu())));
        when(menuMapper.toDto(any())).thenReturn(new MenuDto());

        assertThat(menuService.findMenuByRestaurantId(resId, pageable)).hasSize(1);
    }

    @Test
    @DisplayName("findMenuByRestaurantId: restaurant not found")
    void findMenuByRestaurantId_NotFound() {
        Long id = 1L;
        PageRequest pageable = PageRequest.of(0, 10);

        when(restaurantRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> menuService.findMenuByRestaurantId(id, pageable))
                .isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    @DisplayName("addMenu: success with dishes")
    void addMenu_Success_WithDishes() {
        MenuCreateDto dto = new MenuCreateDto();
        dto.setRestaurantId(1L);
        dto.setDishesIds(List.of(10L));

        Menu menu = new Menu();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        menu.setRestaurant(restaurant);

        when(menuMapper.toEntity(dto)).thenReturn(menu);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(dishRepository.findAllById(dto.getDishesIds())).thenReturn(List.of(new Dish()));
        when(menuRepository.save(any())).thenReturn(menu);
        when(menuMapper.toDto(any())).thenReturn(new MenuDto());

        assertThat(menuService.addMenu(dto)).isNotNull();
    }

    @Test
    @DisplayName("addMenu: success without dishes ids (Branch Coverage)")
    void addMenu_Success_NoDishes() {
        MenuCreateDto dto = new MenuCreateDto();
        dto.setRestaurantId(1L);
        dto.setDishesIds(null);

        Menu menu = new Menu();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        menu.setRestaurant(restaurant);

        when(menuMapper.toEntity(dto)).thenReturn(menu);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(menuRepository.save(any())).thenReturn(menu);
        when(menuMapper.toDto(any())).thenReturn(new MenuDto());

        menuService.addMenu(dto);
        verify(dishRepository, never()).findAllById(any());
    }

    @Test
    @DisplayName("addMenu: restaurant not found")
    void addMenu_RestaurantNotFound() {
        MenuCreateDto dto = new MenuCreateDto();
        dto.setRestaurantId(1L);

        Menu menu = new Menu();
        Restaurant mockRestaurant = new Restaurant();
        mockRestaurant.setId(1L);
        menu.setRestaurant(mockRestaurant);

        when(menuMapper.toEntity(dto)).thenReturn(menu);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> menuService.addMenu(dto))
                .isInstanceOf(RestaurantNotFoundException.class)
                .hasMessageContaining("Restaurant not found with id: 1");
    }

    @Test
    @DisplayName("updateMenuById: success with new restaurant and dishes")
    void updateMenuById_FullUpdate() {
        Long id = 1L;
        MenuCreateDto dto = new MenuCreateDto();
        dto.setRestaurantId(2L);
        dto.setDishesIds(List.of(10L));

        Menu existing = new Menu();
        Restaurant oldRes = new Restaurant(); oldRes.setId(1L);
        existing.setRestaurant(oldRes);

        Restaurant newRes = new Restaurant(); newRes.setId(2L);

        when(menuRepository.findWithRestaurantAndDishesById(id)).thenReturn(Optional.of(existing));
        when(restaurantRepository.findById(2L)).thenReturn(Optional.of(newRes));
        when(dishRepository.findAllById(any())).thenReturn(List.of(new Dish()));
        when(menuRepository.save(any())).thenReturn(existing);
        when(menuMapper.toDto(any())).thenReturn(new MenuDto());

        menuService.updateMenuById(id, dto);
        assertThat(existing.getRestaurant().getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("updateMenuById: same restaurant and null dishes (Branch Coverage)")
    void updateMenuById_SameRestaurant_NoDishes() {
        Long id = 1L;
        MenuCreateDto dto = new MenuCreateDto();
        dto.setRestaurantId(1L);
        dto.setDishesIds(null);

        Menu existing = new Menu();
        Restaurant res = new Restaurant(); res.setId(1L);
        existing.setRestaurant(res);

        when(menuRepository.findWithRestaurantAndDishesById(id)).thenReturn(Optional.of(existing));
        when(menuRepository.save(any())).thenReturn(existing);
        when(menuMapper.toDto(any())).thenReturn(new MenuDto());

        menuService.updateMenuById(id, dto);
        verify(restaurantRepository, never()).findById(any());
        verify(dishRepository, never()).findAllById(any());
    }

    @Test
    @DisplayName("updateMenuById: not found")
    void updateMenuById_NotFound() {
        Long id = 1L;
        MenuCreateDto dto = new MenuCreateDto();
        when(menuRepository.findWithRestaurantAndDishesById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> menuService.updateMenuById(id, dto))
                .isInstanceOf(MenuNotFoundException.class);
    }

    @Test
    @DisplayName("updateMenuById: restaurant not found")
    void updateMenuById_RestaurantNotFound() {
        MenuCreateDto dto = new MenuCreateDto();
        dto.setRestaurantId(2L);
        Menu existing = new Menu();
        Restaurant oldRes = new Restaurant(); oldRes.setId(1L);
        existing.setRestaurant(oldRes);

        when(menuRepository.findWithRestaurantAndDishesById(1L)).thenReturn(Optional.of(existing));
        when(restaurantRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> menuService.updateMenuById(1L, dto))
                .isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    @DisplayName("addDishToMenu: success")
    void addDishToMenu_Success() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(new Menu()));
        when(dishRepository.findById(2L)).thenReturn(Optional.of(new Dish()));
        when(menuMapper.toDto(any())).thenReturn(new MenuDto());

        menuService.addDishToMenu(1L, 2L);
        verify(dishRepository).save(any());
    }

    @Test
    @DisplayName("addDishToMenu: dish not found")
    void addDishToMenu_DishNotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(new Menu()));
        when(dishRepository.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> menuService.addDishToMenu(1L, 2L)).isInstanceOf(DishNotFoundException.class);
    }

    @Test
    @DisplayName("deleteMenuById: success")
    void deleteMenuById_Success() {
        when(menuRepository.existsById(1L)).thenReturn(true);
        when(orderRepository.findByMenuId(1L)).thenReturn(Collections.emptyList());

        menuService.deleteMenuById(1L);
        verify(menuRepository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteMenuById: not found")
    void deleteMenuById_NotFound() {
        when(menuRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> menuService.deleteMenuById(1L)).isInstanceOf(MenuNotFoundException.class);
    }

    @Test
    @DisplayName("deleteMenuById: has orders (Branch Coverage)")
    void deleteMenuById_HasOrders() {
        Long menuId = 1L;
        com.example.fooddelivery.entity.Order mockOrder = new com.example.fooddelivery.entity.Order();

        when(menuRepository.existsById(menuId)).thenReturn(true);

        when(orderRepository.findByMenuId(menuId)).thenReturn(List.of(mockOrder));

        assertThatThrownBy(() -> menuService.deleteMenuById(menuId))
                .isInstanceOf(MenuHasDishesException.class)
                .hasMessageContaining("Menu with id " + menuId + "has dishes and cannot be delete");

        verify(menuRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("addDishesToMenu: bulk success with categories")
    void addDishesToMenu_Bulk_Success() {
        Long menuId = 1L;
        Menu menu = new Menu();
        menu.setDishes(new ArrayList<>());

        DishCreateDto d1 = new DishCreateDto(); d1.setCategoryId(10L);
        DishCreateDto d2 = new DishCreateDto(); d2.setCategoryId(null);

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
        when(dishMapper.toEntity(any())).thenReturn(new Dish());
        when(categoryRepository.findById(10L)).thenReturn(Optional.of(new Category()));
        when(menuMapper.toDto(any())).thenReturn(new MenuDto());

        menuService.addDishesToMenu(menuId, List.of(d1, d2));

        verify(dishRepository).saveAll(any());
        assertThat(menu.getDishes()).hasSize(2);
    }

    @Test
    @DisplayName("addDishesToMenu: bulk category not found")
    void addDishesToMenu_Bulk_CategoryNotFound() {
        DishCreateDto d1 = new DishCreateDto();
        d1.setCategoryId(10L);
        List<DishCreateDto> dishesDto = List.of(d1);
        when(menuRepository.findById(1L)).thenReturn(Optional.of(new Menu()));
        when(dishMapper.toEntity(any())).thenReturn(new Dish());
        when(categoryRepository.findById(10L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> menuService.addDishesToMenu(1L, dishesDto))
                .isInstanceOf(CategoryNotFoundException.class);
    }
}