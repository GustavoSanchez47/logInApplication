package com.mimacom.training.demospringsecurity3.controller;

import com.mimacom.training.demospringsecurity3.model.Task;
import com.mimacom.training.demospringsecurity3.model.User;
import com.mimacom.training.demospringsecurity3.service.TaskService;
import com.mimacom.training.demospringsecurity3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
public class UserTaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;
    @GetMapping("/index")
    public String allTasksOfUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        extracted(model);
        return "index";
    }

    private void extracted(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verificamos si la autenticación es válida y tiene detalles de usuario
        if (authentication != null && authentication.isAuthenticated()) {
            // Obtenemos el nombre de usuario del principal
            String username = authentication.getName();

            // Agregamos el nombre de usuario al modelo para que esté disponible en la vista
            model.addAttribute("username", username);
            User user = userService.findByUsername(username);
            //model.addAttribute("user", user);
            model.addAttribute("tasks", taskService.findAssignedByUser(user));

        }
    }

    @GetMapping("/insertForm")
    public String insertClient(Model model) {
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("task", new Task());
        return "insertForm";

    }
    @PostMapping("/insertSubmit")
    public String insertSubmit(@ModelAttribute Task task, @RequestParam("users") List<User> users, Model model) throws AccessDeniedException {
        task.setUsers(users);
        taskService.save(task);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        extracted(model);
        // Verificamos si la autenticación es válida y tiene detalles de usuario

        return "index";
    }
    @GetMapping("/delete")
    public String deleteTask(@ModelAttribute Task task,Model model) {
        taskService.deleteTask(task);
        extracted(model);
        return "index";
    }
    @GetMapping("/modify")
    public String modifyTask(@RequestParam Long id, Model model) {
        //Task task= taskService.findTaskById(id);
        model.addAttribute("allUsers", userService.findAll());
       // model.addAttribute("usersAsigned",task.getUsers());
        model.addAttribute("task", taskService.findTaskById(id));
        return "modifyForm";
    }
    @PostMapping("/modifySubmit")
    public String modifySubmit(@ModelAttribute Task task, @RequestParam("users") List<User> users, Model model) throws AccessDeniedException {
        task.setUsers(users);
        taskService.modify(task);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        extracted(model);
        // Verificamos si la autenticación es válida y tiene detalles de usuario

        return "index";
    }


}
