package com.capstone.stayahead.service;

import com.capstone.stayahead.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Manage UsernameNotFound exception at GlobalExceptionHandler
        return usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
