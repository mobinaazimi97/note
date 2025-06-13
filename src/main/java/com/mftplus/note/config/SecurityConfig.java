package com.mftplus.note.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // اگر API یا فرم‌های ساده داری؛ برای امنیت بیشتر بهتره فعال باشه
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/css/", "/js/").permitAll() // صفحات عمومی
                        .anyRequest().authenticated()  // بقیه صفحات نیاز به لاگین دارن
                )
                .formLogin(form -> form
                        .loginPage("/login")   // صفحه لاگین خودت
                        .defaultSuccessUrl("/notes", true)  // بعد از لاگین موفق به یادداشت‌ها می‌ره
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
