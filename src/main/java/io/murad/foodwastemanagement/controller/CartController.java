package io.murad.foodwastemanagement.controller;

import io.murad.foodwastemanagement.model.Cart;
import io.murad.foodwastemanagement.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public String viewCart(Model model, Principal principal) {
        String username = principal.getName();
        Cart cart = cartService.getCartByUsername(username);
//        System.out.println(cart.getUser().getRole());
        model.addAttribute("cart", cart);
        return "carts/cart";
    }

    @PostMapping("/addToCart/{foodId}")
    public String addToCart(@PathVariable("foodId") Long foodId, Principal principal, @RequestParam("quantity") int quantity) {
        String username = principal.getName();
        cartService.addToCart(username, foodId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove/{foodId}")
    public String removeFromCart(@PathVariable("foodId") Long foodId, Principal principal) {
        String username = principal.getName();
        cartService.removeFromCart(username, foodId);
        return "redirect:/cart";
    }
}

