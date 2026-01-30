package com.capstone.stayahead.config;

import com.capstone.stayahead.model.EnumRole;
import com.capstone.stayahead.model.Users;
import com.capstone.stayahead.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Value("${ADMIN}")
    private String ADMIN;

    @Override
    public void run(String... args) {
        if (usersRepository.findByEmail("admin@stayahead.com").isEmpty()) {
            Users admin = new Users();
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setEmail("admin@stayahead.com");
            admin.setPassword(passwordEncoder.encode(ADMIN));
            admin.setRole(EnumRole.valueOf("ADMIN"));
            usersRepository.save(admin);
        }
    }
}

