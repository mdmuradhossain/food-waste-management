package io.murad.foodwastemanagement.service;

import io.murad.foodwastemanagement.model.*;
import io.murad.foodwastemanagement.repository.CartRepository;
import io.murad.foodwastemanagement.repository.FoodRepository;
import io.murad.foodwastemanagement.repository.OrderRepository;
import io.murad.foodwastemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

//    public void placeOrder(String username, Cart cart) {
//        User user = userRepository.findByUsername(username);
//        if (user != null && cart != null && !cart.getItems().isEmpty()) {
//            Order order = new Order();
//            List<CartItem> items = new ArrayList<>(cart.getItems());
//            items.forEach(item -> item.setOrder(order)); // Associate the Order with each CartItem
//            order.setUser(user); // Associate the User with the Order
//            order.setItems(items);
//            orderRepository.save(order); // Save the Order
//            cart.getItems().clear(); // Clear the items from the Cart
//            cartRepository.save(cart); // Update the Cart (optional, if needed)
//        }
//    }

    public void placeOrder(String username, Cart cart) {
        User user = userRepository.findByUsername(username);
        if (user != null && cart != null && !cart.getItems().isEmpty()) {
            Order order = new Order();
            List<CartItem> items = new ArrayList<>(cart.getItems());
            items.forEach(item -> {
                item.setOrder(order); // Associate the Order with each CartItem
                item.setCart(null); // Dissociate the CartItem from the Cart
            });
            order.setUser(user); // Associate the User with the Order
            order.setItems(items);
            order.setIsProcessing(true);
            orderRepository.save(order); // Save the Order

            for (CartItem item : items) {
                Food food = item.getFood();
                int quantity = item.getQuantity();
                int newQuantity = food.getQuantity() - quantity;
                food.setQuantity(newQuantity);
                foodRepository.save(food);
            }

            cart.getItems().clear(); // Clear the items from the Cart
            cartRepository.save(cart); // Update the Cart (optional, if needed)
        }
    }

    public List<Order> getOrdersByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? user.getOrders() : new ArrayList<>();
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).get();
    }
}
