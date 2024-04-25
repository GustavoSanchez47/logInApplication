package com.mimacom.training.demospringsecurity3.service;

import com.mimacom.training.demospringsecurity3.model.Task;
import com.mimacom.training.demospringsecurity3.model.User;
import com.mimacom.training.demospringsecurity3.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> findAssignedByUser(User user) {
        return taskRepository.findAllByUsersContaining(user);
    }
    @Override
    public Task save(Task task) throws AccessDeniedException {
        // Verificamos si el usuario tiene el rol de administrador
        if (!isAdmin()) {
            throw new AccessDeniedException("Only administrators can create tasks.");
        }

        // Guardamos la tarea en la base de datos
        return taskRepository.save(task);
    }
    @Override
    public Task modify(Task task)  {
        // Verificamos si el usuario tiene el rol de administrador


        // Guardamos la tarea en la base de datos
        return taskRepository.save(task);
    }
    @Override
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }
    @Override
    public Task findTaskById(long id) {
        return taskRepository.findById(id).get();
    }
    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }



}
