package com.mimacom.training.demospringsecurity_2.repository;

import com.mimacom.training.demospringsecurity_2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
