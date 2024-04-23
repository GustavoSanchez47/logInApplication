package com.mimacom.training.demospringsecurity3.repository;

import com.mimacom.training.demospringsecurity3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    //User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email = :query OR u.username = :query")
    User findByEmailOrUsername(@Param("query") String query);
}
