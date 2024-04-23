package io.murad.foodwastemanagement.service;

import io.murad.foodwastemanagement.model.Cart;
import io.murad.foodwastemanagement.model.CartItem;
import io.murad.foodwastemanagement.model.Food;
import io.murad.foodwastemanagement.model.User;
import io.murad.foodwastemanagement.repository.CartItemRepository;
import io.murad.foodwastemanagement.repository.CartRepository;
import io.murad.foodwastemanagement.repository.FoodRepository;
import io.murad.foodwastemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
//@Transactional
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart getCartByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? user.getCart() : null;
    }

    @Transactional
    public void addToCart(String username, Long foodId, int quantity) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Food food = foodRepository.findById(foodId).orElseThrow(()-> new NoSuchElementException("Food is empty"));
            if (food != null) {
                Cart cart = user.getCart();
                if (cart == null) {
                    cart = new Cart();
                    cart.setUser(user);
                    cart = cartRepository.save(cart); // Save the cart and retrieve the updated instance
                    user.setCart(cart);
                }
                // Check if the item is already in the cart
                CartItem cartItem = cartItemRepository.findByCartAndFood(cart, food);
                if (cartItem != null) {
                    // Increment the quantity if the item is already in the cart
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                } else {
                    // Create a new cart item and add it to the cart
                    cartItem = new CartItem();
                    cartItem.setFood(food);
                    cartItem.setQuantity(quantity);
                    cartItem.setCart(cart);
                    cart.getItems().add(cartItem); // Add the item to the cart
                }
                // Save the cart item
                cartItemRepository.save(cartItem);
            }
        } else {
            // Log error or handle user not found
            System.out.println("User not found for username: " + username);
        }
    }


    public void removeFromCart(String username, Long foodId) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Food food = foodRepository.findById(foodId).orElse(null);
            if (food != null) {
                Cart cart = user.getCart();
                if (cart != null) {
                    CartItem cartItem = cartItemRepository.findByCartAndFood(cart, food);
                    if (cartItem != null) {
                        int quantity = cartItem.getQuantity();
                        if (quantity > 1) {
                            cartItem.setQuantity(quantity - 1);
                        } else {
                            cart.getItems().remove(cartItem);
                            cartItemRepository.delete(cartItem);
                        }
                        cartRepository.save(cart);
                    }
                }
            }
        }
    }
}
