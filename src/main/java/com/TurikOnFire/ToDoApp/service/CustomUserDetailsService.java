package com.TurikOnFire.ToDoApp.service;

import com.TurikOnFire.ToDoApp.config.CustomUserDetails;
import com.TurikOnFire.ToDoApp.entity.Authority;
import com.TurikOnFire.ToDoApp.entity.UserEntity;
import com.TurikOnFire.ToDoApp.repository.AuthorityRepository;
import com.TurikOnFire.ToDoApp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public CustomUserDetailsService(UserRepository userRepository,
                                      AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> roles = authorityRepository.findByUsername(username)
                .stream()
                .map(authority -> new SimpleGrantedAuthority("ROLE_" + authority.getAuthority())
                )
                .toList();

        return new CustomUserDetails(user.getUsername(), user.getPassword(), roles, user.getId());
    }
}