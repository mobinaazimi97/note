package com.mftplus.note.controller;

import com.mftplus.note.model.entity.User;
import com.mftplus.note.model.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";  // به فایل templates/users/list.html اشاره دارد
    }

    // صفحه نمایش جزئیات یک کاربر
    @GetMapping("/{id}")
    public String userDetails(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/detail";  // به فایل templates/users/detail.html اشاره دارد
    }

    // صفحه فرم ثبت نام/ایجاد کاربر جدید
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "users/create";  // به فایل templates/users/create.html اشاره دارد
    }

    // ثبت نام/ایجاد کاربر جدید
    @PostMapping
    public String createUser(@ModelAttribute User user) {
        if (userService.existsByUsername(user.getUsername())) {
            // می‌تونی این خطا رو توی صفحه نمایش بدی یا مدل خطا اضافه کنی
            return "redirect:/users/new?error=usernameExists";
        }
        userService.saveUser(user);
        return "redirect:/users";
    }

    // صفحه فرم ویرایش اطلاعات کاربر
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/edit";  // به فایل templates/users/edit.html اشاره دارد
    }

    // ویرایش کاربر
    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User updatedUser) {
        try {
            userService.updateUser(id, updatedUser);
        } catch (RuntimeException e) {
            // در صورت بروز خطا می‌توانی مدل خطا بگذاری یا ریدایرکت به صفحه ویرایش با پیام خطا
            return "redirect:/users/" + id + "/edit?error=" + e.getMessage();
        }
        return "redirect:/users/" + id;
    }

//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//        return "users/register";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute("user") User user, Model model) {
//        try {
//            if (userService.existsByUsername(user.getUsername())) {
//                model.addAttribute("error", "نام کاربری قبلاً انتخاب شده است");
//                return "users/register";
//            }
//            userService.saveUser(user);
//            return "redirect:/login";
//        } catch (Exception e) {
//            model.addAttribute("error", "خطا در ثبت نام: " + e.getMessage());
//            return "users/register";
//        }
//    }
//
//    @GetMapping("/profile")
//    public String userProfile(Model model) {
//        model.addAttribute("user", userService.getCurrentUser());
//        return "users/profile";
//    }
    //--------------------------------------------------------------------

//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//        return "users/register";
//    }

//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute("user") User user) {
//        userService.saveUser(user);
//        return "redirect:/login";
//    }

//    @GetMapping("/profile")
//    public String userProfile(Model model) {
//        model.addAttribute("user", userService.getCurrentUser());
//        return "users/profile";
//    }
}
