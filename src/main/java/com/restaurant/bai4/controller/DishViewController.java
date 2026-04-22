package com.restaurant.bai4.controller;

import com.restaurant.common.Dish;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DishViewController {

    @GetMapping("/bai4/dishes")
    public String list(Model model) {

        model.addAttribute("dishes", List.of(
                new Dish(1, "Phở", 50000, true),
                new Dish(2, "Bún", 40000, true)
        ));

        return "dish-list";
    }

    @GetMapping("/bai4/detail")
    public String detail(Model model) {

        model.addAttribute("dish",
                new Dish(1, "Phở", 50000, true));

        return "dish-detail";
    }
}