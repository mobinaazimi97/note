package com.mftplus.note.controller;

import com.mftplus.note.model.entity.User;
import com.mftplus.note.model.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/list";  // مسیر صفحه Thymeleaf: src/main/resources/templates/users/list.html
    }

    // OrderBy ID
    @GetMapping("/{username}")
    public String userDetails(@PathVariable String username, Model model) {
        userService.getUserById(username).ifPresent(user -> model.addAttribute("user", user));
        return "users/details"; // مسیر صفحه Thymeleaf: src/main/resources/templates/users/details.html
    }

    // signup new user
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "users/register"; // مسیر صفحه Thymeleaf: src/main/resources/templates/users/register.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        // می‌توان اینجا اعتبارسنجی انجام داد

        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "نام کاربری تکراری است!");
            return "users/register";
        }

        userService.registerUser(user);
        return "redirect:/users";  // پس از ثبت‌نام به لیست کاربران می‌رود
    }
}
