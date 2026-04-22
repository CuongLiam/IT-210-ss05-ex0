package com.restaurant.bai2.service;

import com.restaurant.common.Dish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    public List<Dish> getAllDishes() {
        return List.of(
                new Dish(1, "Phở bò", 50000, true),
                new Dish(2, "Bún chả", 45000, false),
                new Dish(3, "Cơm tấm", 40000, true)
        );
    }
}