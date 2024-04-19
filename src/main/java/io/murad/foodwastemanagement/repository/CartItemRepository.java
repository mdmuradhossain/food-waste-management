package io.murad.foodwastemanagement.repository;

import io.murad.foodwastemanagement.model.Cart;
import io.murad.foodwastemanagement.model.CartItem;
import io.murad.foodwastemanagement.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndFood(Cart cart, Food food);
}
