package com.mimacom.training.demospringsecurity3.controller;

import com.mimacom.training.demospringsecurity3.model.Task;
import com.mimacom.training.demospringsecurity3.service.TaskService;
import com.mimacom.training.demospringsecurity3.service.TaskServiceImpl;
import com.mimacom.training.demospringsecurity3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api-tasks")
public class TaskManagerRestController {
    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;

    @GetMapping
    public List<Task> getTasks() {
        return taskService.findAllTasks();
    }
    @GetMapping("/tasks-of/{userName}")
    public List<Task> getTasksofUser(@PathVariable String userName) {
        return taskService.findAssignedByUser(userService.findByUsername(userName));

    }

    @PostMapping("/save")
    public String saveTask(@RequestBody Task task, Errors errors) throws AccessDeniedException {
        if (errors.hasErrors()) {
            String result="";
            for (ObjectError error : errors.getAllErrors()) {
                result += error.getDefaultMessage()+" ";
            }

        return result;
        }
        taskService.save(task);
        return "Task saved with Id: " + task.getId();

    }


}
