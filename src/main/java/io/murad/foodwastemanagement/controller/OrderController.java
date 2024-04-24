package io.murad.foodwastemanagement.controller;

import io.murad.foodwastemanagement.model.Cart;
import io.murad.foodwastemanagement.model.Order;
import io.murad.foodwastemanagement.service.CartService;
import io.murad.foodwastemanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @PostMapping("/placeOrder")
    public String placeOrder(Principal principal) {
        String username = principal.getName();
        Cart cart = cartService.getCartByUsername(username);
        orderService.placeOrder(username, cart);
        return "redirect:/order";
    }

    @GetMapping("")
    public String viewOrders(Model model, Principal principal) {
        String username = principal.getName();
        List<Order> orders = orderService.getOrdersByUsername(username);
        model.addAttribute("orders", orders);
        return "orders/order";
    }

    @GetMapping("/{id}")
    public String editOrderForm(Model model, @PathVariable("id") Long id) {
        Order order = orderService.getOrder(id);
        model.addAttribute("order", order);
        return "orders/edit_order";
    }

    @PutMapping("/{id}")
    public String updateOrder(@PathVariable("id") Long id, @ModelAttribute("order") Order order) {
        order.setId(id);
        orderService.save(order);
        return "redirect:/order";
    }

    @DeleteMapping("/{id}")
    public void removeOrder(@PathVariable("id") Long id){
        orderService.deleteOrder(id);
    }
}

