package com.mftplus.note.model.service;

import com.mftplus.note.model.entity.User;
import com.mftplus.note.model.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository; // فرض کن ریپازیتوری داری

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("کاربر یافت نشد: " + username));


//        User user1 = User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .role(user.getRole())
//                .build();
//        return ;
        org.springframework.security.core.userdetails.User.UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(user.getPassword());
        builder.roles(user.getRole());

        return builder.build();
    }

}
