package com.TurikOnFire.ToDoApp.config;

import com.TurikOnFire.ToDoApp.repository.AuthorityRepository;
import com.TurikOnFire.ToDoApp.repository.UserRepository;
import com.TurikOnFire.ToDoApp.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "ROOT")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN", "ROOT")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler((request, response, auth) -> {
                            response.sendRedirect("/home");
                        })
                )
                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, auth) -> {
                            response.sendRedirect("/login?logout");
                        })
                        .clearAuthentication(true) // Очистка аутентификации
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

//    Аутентификация встроенная "InMemory"
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder.encode("password"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails root = User.builder()
//                .username("root")
//                .password(passwordEncoder.encode("root"))
//                .roles("ADMIN", "USER", "ROOT")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin, root);
//    }

//    Аутентификация через БД
    @Bean
    public UserDetailsService userDetailsService(UserRepository dataSource, AuthorityRepository authorityRepository) {
        return new CustomUserDetailsService(dataSource, authorityRepository);
    }

//    Шифрование паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
