package io.murad.foodwastemanagement.controller;

import io.murad.foodwastemanagement.model.User;
import io.murad.foodwastemanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProfileController {

    private UserService userService;

    @GetMapping("/user")
    public String profile(Model model) {
        List<User> users = userService.users();
        model.addAttribute("users", users);
        return "profile";
    }

}
