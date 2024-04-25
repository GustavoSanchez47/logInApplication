package com.mimacom.training.demospringsecurity3.service;

import com.mimacom.training.demospringsecurity3.dto.UserRegistrationDTO;
import com.mimacom.training.demospringsecurity3.model.Role;
import com.mimacom.training.demospringsecurity3.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDTO userDto);
    public List<User> findAll();
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
     User findByUsername(String username);
    default Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }
}
