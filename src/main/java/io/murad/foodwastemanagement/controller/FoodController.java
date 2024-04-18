package io.murad.foodwastemanagement.controller;

import io.murad.foodwastemanagement.model.Food;
import io.murad.foodwastemanagement.service.FoodService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Controller
//@RequestMapping(value = "/donor-dashboard")
public class FoodController {

    private FoodService foodService;

    @GetMapping(value = "/foods")
    public String getAllFoods(Model model) {
        List<Food> foods = foodService.findAll();
        model.addAttribute("foods", foods);
        return "foods/display_foods";
    }

    @GetMapping("/foods/showAddFoodForm")
    public String addFood(Model model) {
        model.addAttribute("food",new Food());
        return "foods/add_food";
    }

    @PostMapping("/foods/add")
    public String addFood(@ModelAttribute("food") Food food) {
        foodService.save(food);
        return "Food Added";
    }

    @PutMapping("/foods/{id}")
    public String updateFood(@PathVariable Long id, @ModelAttribute("food") Food updatedFood) {
        updatedFood.setId(id);
        foodService.save(updatedFood);
        return "Food Updated";
    }

    @DeleteMapping("/foods/{id}")
    public void deleteFood(@PathVariable("id") Long id) {
        foodService.deleteById(id);
    }
}
