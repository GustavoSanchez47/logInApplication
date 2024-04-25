package com.mimacom.training.demospringsecurity3.service;

import com.mimacom.training.demospringsecurity3.dto.UserRegistrationDTO;
import com.mimacom.training.demospringsecurity3.model.Role;
import com.mimacom.training.demospringsecurity3.model.User;
import com.mimacom.training.demospringsecurity3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User save(UserRegistrationDTO userDto) {
        String roleName = userDto.getIsAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
       // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User newUser = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()),
                userDto.getEmail(), List.of(new Role(roleName)));
        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("invalid username or password "+ username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),mapRolesToAuthorities(user.getRoles()) );
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByEmailOrUsername(username);
    }
}
