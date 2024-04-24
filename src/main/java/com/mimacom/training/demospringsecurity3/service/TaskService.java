package com.mimacom.training.demospringsecurity3.service;

import com.mimacom.training.demospringsecurity3.model.Task;
import com.mimacom.training.demospringsecurity3.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface TaskService {
    List<Task> findAssignedByUser(User user);

    Task save(Task task) throws AccessDeniedException;

    void deleteTask(Task task);

    Task findTaskById(long id);

    default boolean isAdmin() {
        // Obtenemos la autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verificamos si el usuario tiene el rol de administrador
        return authentication != null && authentication.isAuthenticated() &&
                authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
