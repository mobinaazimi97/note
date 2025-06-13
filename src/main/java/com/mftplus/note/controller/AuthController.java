package com.mftplus.note.controller;

import com.mftplus.note.dto.UserRegistrationDto;
import com.mftplus.note.model.entity.User;
import com.mftplus.note.model.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegistrationDto userDto, Model model) {
        if(userRepository.existsById(userDto.getUsername())) {
            model.addAttribute("error", "نام کاربری قبلا ثبت شده");
            return "register";
        }
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            model.addAttribute("error", "رمز عبور و تکرار آن برابر نیستند");
            return "register";
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        return "redirect:/login?registered";
    }
}
