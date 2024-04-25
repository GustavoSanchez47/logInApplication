package com.mimacom.training.demospringsecurity3.controller;

import com.mimacom.training.demospringsecurity3.dto.UserRegistrationDTO;
import com.mimacom.training.demospringsecurity3.model.User;
import com.mimacom.training.demospringsecurity3.service.TaskService;
import com.mimacom.training.demospringsecurity3.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

  //  private TaskService taskService;
    private UserService userService;
    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }
    @ModelAttribute("user")
    public UserRegistrationDTO userRegistrationDTO() {
        return new UserRegistrationDTO();
    }


    @GetMapping
    public String registrationForm() {
        return "registration";
    }
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user")  @Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/registration?error";
        }
        userService.save(userRegistrationDTO);
        return "redirect:/registration?success";
    }


}
