package io.murad.foodwastemanagement.controller;


import io.murad.foodwastemanagement.model.Category;
import io.murad.foodwastemanagement.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/showCategoryForm")
    public String showCategoryForm(Model model){
        model.addAttribute("category",new Category());
        return "category/category_form";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") Category category){
        categoryService.save(category);
        return "redirect:/category/all";
    }

    @GetMapping("/all")
    public String displayCategories(Model model){
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories",categories);
        return "category/categories";
    }
}
