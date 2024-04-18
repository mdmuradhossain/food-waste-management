package io.murad.foodwastemanagement.service;

import io.murad.foodwastemanagement.model.Food;
import io.murad.foodwastemanagement.repository.FoodRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;
    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public void save(Food food) {
        foodRepository.save(food);
    }

    public void deleteById(Long id) {
        foodRepository.deleteById(id);
    }
}
