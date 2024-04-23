package io.murad.foodwastemanagement.controller;

import io.murad.foodwastemanagement.model.Feedback;
import io.murad.foodwastemanagement.model.Food;
import io.murad.foodwastemanagement.model.User;
import io.murad.foodwastemanagement.service.CategoryService;
import io.murad.foodwastemanagement.service.FoodService;
import io.murad.foodwastemanagement.service.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

//@AllArgsConstructor
@NoArgsConstructor
@Controller
//@RequestMapping(value = "/donor-dashboard")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;
//    public FoodController(FoodService foodService){
//        this.foodService = foodService;
//    }

    @GetMapping(value = "/foods")
    public String getAllFoods(Model model) {
        List<Food> foods = foodService.findAll();
        model.addAttribute("foods", foods);
        return "foods/display_foods";
    }

    @GetMapping("/foods-by-donor")
    public String getFoodsByUser(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUser(username);
        List<Food> foodsByUser = foodService.getFoodByUser(user);
        if (foodsByUser.isEmpty()) {
            System.out.println("Food is empty for current user");
        }
        model.addAttribute("foods", foodsByUser);
        return "foods/foods_from_user";
    }

    @GetMapping("/food/{id}")
    public String showFood(@PathVariable("id") Long id, Model model) {
//        Food food = foodService.findByTitle(title);
        Food food = foodService.findById(id);
        if (food == null) {
            throw new RuntimeException("Food is null");
        }
        model.addAttribute("food", food);
        model.addAttribute("feedback", new Feedback());
        return "foods/show_food";
    }

    @GetMapping("/foods/showAddFoodForm")
    public String addFood(Model model) {
        model.addAttribute("food", new Food());
        model.addAttribute("categories", categoryService.getCategories());
        return "foods/add_food";
    }

    @PostMapping("/foods/add")
    public String addFood(@ModelAttribute("food") Food food, Model model, Principal principal,@RequestParam("file") MultipartFile file) throws IOException {
        String username = principal.getName();
        User user = userService.getUser(username);
        food.setUser(user);

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        food.setImageName(fileName);
        foodService.save(food);

        Path uploadPath = Paths.get("uploads/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path imagePath = uploadPath.resolve(file.getOriginalFilename());
            System.out.println(imagePath.toFile().getAbsolutePath());
            Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("could not save");
        }
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
