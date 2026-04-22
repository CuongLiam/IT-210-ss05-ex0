package com.restaurant.bai3.controller;

import com.restaurant.bai3.service.AdminDishService;
import com.restaurant.common.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminDishController {

    @Autowired
    private AdminDishService service;

//    @GetMapping("/bai3/edit/{id}")
//    public String editDish(@PathVariable int id, Model model) {
//
//        var dishOpt = service.findById(id);
//
//        // ❌ không tìm thấy → redirect + báo lỗi
//        if (dishOpt.isEmpty()) {
//            model.addAttribute("error", "Không tìm thấy món ăn yêu cầu!");
//            return "redirect:/bai2/dishes";
//        }
//
//        // ✅ tìm thấy
//        model.addAttribute("dish", dishOpt.get());
//        return "edit-dish";
//    }

    // fix:

    @GetMapping("/bai3/edit/{id}")
    public String editDish(@PathVariable int id, Model model, RedirectAttributes ra) {

        var dishOpt = service.findById(id);

        if (dishOpt.isEmpty()) {
            ra.addFlashAttribute("error", "Không tìm thấy món ăn yêu cầu!");
            return "redirect:/bai2/dishes";
        }

        model.addAttribute("dish", dishOpt.get());
        return "edit-dish";
    }



}