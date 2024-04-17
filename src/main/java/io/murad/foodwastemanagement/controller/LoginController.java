package io.murad.foodwastemanagement.controller;

import io.murad.foodwastemanagement.model.User;
import io.murad.foodwastemanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class LoginController {

    private UserRepository userRepository;
    @GetMapping("/sign-in")
    public String showLoginPage() {
        return "authenticate/login";
    }

    @GetMapping("/login-success")
    public String loginSuccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (user.getRole().toString().equals("DONOR")) {
            return "redirect:/donor-dashboard";
        } else {
            return "redirect:/consumer-dashboard";
        }
    }
}
