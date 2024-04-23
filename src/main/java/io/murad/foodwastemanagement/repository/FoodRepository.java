package io.murad.foodwastemanagement.repository;

import io.murad.foodwastemanagement.model.Category;
import io.murad.foodwastemanagement.model.Food;
import io.murad.foodwastemanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    Food findByName(String name);

    List<Food> findFoodsByUser(User user);

    List<Food> findFoodsByCategory(Category category);
}
