package com.mimacom.training.demospringsecurity3.repository;

import com.mimacom.training.demospringsecurity3.model.Task;
import com.mimacom.training.demospringsecurity3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUsersContaining(User user);
}
