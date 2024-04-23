package com.mimacom.training.demospringsecurity_2.service;

import com.mimacom.training.demospringsecurity_2.dto.UserRegistrationDTO;
import com.mimacom.training.demospringsecurity_2.model.Role;
import com.mimacom.training.demospringsecurity_2.model.User;
import com.mimacom.training.demospringsecurity_2.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User save(UserRegistrationDTO userDto) {
       // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User newUser = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()),
                userDto.getEmail(), List.of(new Role("ROLE_USER")));
        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("invalid username or password "+ username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),mapRolesToAuthorities(user.getRoles()) );
    }
}
