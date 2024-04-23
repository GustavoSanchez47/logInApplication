package com.mimacom.training.demospringsecurity_2.service;

import com.mimacom.training.demospringsecurity_2.dto.UserRegistrationDTO;
import com.mimacom.training.demospringsecurity_2.model.Role;
import com.mimacom.training.demospringsecurity_2.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDTO userDto);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    default Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }
}
