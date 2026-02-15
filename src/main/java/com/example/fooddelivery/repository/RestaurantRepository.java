package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.Restaurant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class RestaurantRepository {
    private List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantRepository() {
        Restaurant r1 = new Restaurant();
        r1.setId(1L);
        r1.setName("Pizza 21");
        r1.setAddress("st. Frunz, 10");
        r1.setCity("Minsk");
        r1.setRating(8.8);

        Restaurant r2 = new Restaurant();
        r2.setId(2L);
        r2.setName("Burger king");
        r2.setAddress("st. Nemiga, 10");
        r2.setCity("Minsk");
        r2.setRating(7.9);

        restaurants.add(r1);
        restaurants.add(r2);
    }

    public List<Restaurant> findAll(){
        return restaurants;
    }

    public Restaurant findById(Long id){
        for (Restaurant res:restaurants){
            if(res.getId().equals(id)){
                return res;
            }
        }
        return null;
    }

    public Restaurant addRestaurant(Restaurant newRestaurant){
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public void deleteRestaurant(Restaurant restaurant){
        restaurants.remove(restaurant);
    }

    public void deleteById(Long id){
        for (Restaurant res:restaurants){
            if(res.getId().equals(id)){
                restaurants.remove(res);
                break;
            }
        }
    }



    public Restaurant updateRestaurant(Long id, Restaurant newRestaurant){
        for (Restaurant res:restaurants){
            if(res.getId().equals(id)) {
                res.setName(newRestaurant.getName());
                res.setAddress(newRestaurant.getAddress());
                res.setRating(newRestaurant.getRating());
                res.setCity(newRestaurant.getCity());
                return res;
            }
        }
        return null;
    }
}
