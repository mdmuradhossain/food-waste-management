package io.murad.foodwastemanagement.controller;

import io.murad.foodwastemanagement.model.Food;
import io.murad.foodwastemanagement.service.FoodService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@AllArgsConstructor
@NoArgsConstructor
@Controller
//@RequestMapping(value = "/donor-dashboard")
public class FoodController {

    @Autowired
    private FoodService foodService;

//    public FoodController(FoodService foodService){
//        this.foodService = foodService;
//    }

    @GetMapping(value = "/foods")
    public String getAllFoods(Model model) {
        List<Food> foods = foodService.findAll();
        model.addAttribute("foods", foods);
        return "foods/display_foods";
    }

    @GetMapping("/food/{title}")
    public String showFood(@PathVariable("title") String title, Model model) {
        Food food = foodService.findByTitle(title);
        if (food == null) {
            throw new RuntimeException("Food is null");
        }
        model.addAttribute("food",food);
        return "foods/show_food";
    }

    @GetMapping("/foods/showAddFoodForm")
    public String addFood(Model model) {
        model.addAttribute("food", new Food());
        return "foods/add_food";
    }

    @PostMapping("/foods/add")
    public String addFood(@ModelAttribute("food") Food food, Model model) {
        foodService.save(food);
        model.addAttribute("message", "Successfully food added.");
        return "redirect:/foods";
    }

    @PutMapping("/foods/{id}")
    public String updateFood(@PathVariable("id") Long id, @ModelAttribute("food") Food updatedFood) {
        updatedFood.setId(id);
        foodService.save(updatedFood);
        return "Food Updated";
    }

    @DeleteMapping("/foods/{id}")
    public void deleteFood(@PathVariable("id") Long id) {
        foodService.deleteById(id);
    }
}
