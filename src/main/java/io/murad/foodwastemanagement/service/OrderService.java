package io.murad.foodwastemanagement.service;

import io.murad.foodwastemanagement.model.Cart;
import io.murad.foodwastemanagement.model.CartItem;
import io.murad.foodwastemanagement.model.Order;
import io.murad.foodwastemanagement.model.User;
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
    private OrderRepository orderRepository;

    public void placeOrder(String username, Cart cart) {
        User user = userRepository.findByUsername(username);
        if (user != null && cart != null && !cart.getItems().isEmpty()) {
            Order order = new Order();
            List<CartItem> items = new ArrayList<>(cart.getItems());
            items.forEach(item -> item.setOrder(order));
            order.setItems(items);
            orderRepository.save(order);
            cart.getItems().clear();
            userRepository.save(user);
        }
    }

    public List<Order> getOrdersByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? user.getOrders() : new ArrayList<>();
    }
}
