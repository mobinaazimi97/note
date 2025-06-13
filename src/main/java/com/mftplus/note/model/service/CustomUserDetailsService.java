package com.mftplus.note.model.service;

import com.mftplus.note.model.entity.CustomUserDetails;
import com.mftplus.note.model.entity.User;
import com.mftplus.note.model.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository; // فرض کن ریپازیتوری داری

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("کاربر یافت نشد: " + username));
        return new CustomUserDetails(user);

//        User user1 = User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .role(user.getRole())
//                .build();
//        return ;

    }

}
