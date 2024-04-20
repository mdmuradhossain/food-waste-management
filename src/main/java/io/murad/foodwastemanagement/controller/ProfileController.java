package io.murad.foodwastemanagement.controller;

import io.murad.foodwastemanagement.model.User;
import io.murad.foodwastemanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class ProfileController {

    private UserService userService;

    @GetMapping("/{username}")
    public String profile(@PathVariable("username") String username, Model model) {
        User user = userService.getUser(username);
        model.addAttribute("username", username);
        model.addAttribute("user", user);
        return "profile";
    }

}
