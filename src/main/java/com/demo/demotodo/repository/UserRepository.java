package com.demo.demotodo.repository;

import com.demo.demotodo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAddress(String userEmailAddress);
    User findUserByEmailAddress(String emailAddress);
}
