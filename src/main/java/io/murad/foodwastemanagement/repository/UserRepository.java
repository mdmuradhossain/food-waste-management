package io.murad.foodwastemanagement.repository;

import io.murad.foodwastemanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
//    Optional<User> findByEmail(String email);
}
