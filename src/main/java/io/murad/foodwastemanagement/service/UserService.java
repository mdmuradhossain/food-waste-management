package io.murad.foodwastemanagement.service;

import io.murad.foodwastemanagement.model.User;
import io.murad.foodwastemanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> users() {
        return userRepository.findAll();
    }
}
