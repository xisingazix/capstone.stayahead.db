package com.capstone.stayahead.service;

import com.capstone.stayahead.model.Users;
import com.capstone.stayahead.repository.UsersRepository;
import com.capstone.stayahead.dto.UserDto;
import com.capstone.stayahead.exception.EmailAlreadyExistsException;
import com.capstone.stayahead.exception.ResourceNotFoundException;
import com.capstone.stayahead.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class AuthService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("${file.upload-dir}")
    private String uploadDir;

    public Users signUp(Users users) throws EmailAlreadyExistsException {

        if (usersRepository.findByEmail(users.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Please use another email.");
        }

        Users _users = Users.builder()
                .email(users.getUsername())
                .password(passwordEncoder.encode((users.getPassword())))
                .build();

       return usersRepository.save(_users);
    }

    @Transactional
    public UserDto signIn(Users users) throws ResourceAccessException{

        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(users.getUsername(), users.getPassword());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

        /**
         * SecurityContextHolder.getContext().setAuthentication(authenticationResponse) - logs the authenticated user
         * authenticationResponse.getPrincipal() - returns an Object (class: UserDetails)
         * Cast returned UserDetails to User entity (userDetails doesn't have access modifier for "userName")
         * Within userDetails "userName" ≠ attribute "username")
         * The typecasting allows the extraction of: userName (≠ username), email and role
         */

        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
        Users _users = (Users) authenticationResponse.getPrincipal();

        String token = jwtUtils.generateToken(_users);
        String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), _users);
        Long expirationTime = jwtUtils.extractExpirationTime(token);

        UserDto userDto = UserDto.builder()
                .email(_users.getUsername())  // Return athenticated user email
                .token(token)                   // Return prepared token
                .refreshToken(refreshToken)     // Return prepared refresh token
                .expirationTime(expirationTime) // Return prepared expiry
                .message("success")             // Return "success" as a message
                .role(_users.getRole())          // Return authenticated user's role
                .build();

        return userDto;
    }

    public UserDto update(Users users, MultipartFile image) throws IOException, ResourceNotFoundException {

        // Obtain the user's identity from Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        // Fetch the managed user
        Users existingUser = usersRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Username will never be not inserted
        existingUser.setEmail(users.getUsername());

        if (users.getPassword() != null)
            existingUser.setPassword(passwordEncoder.encode(users.getPassword()));

        // role should not be updated by an end user

        usersRepository.saveAndFlush(existingUser);

        // Use _user.getId() to ensure user is saved
        if (image != null && !image.isEmpty() && existingUser.getId() != null) {

            // TODO check if the file is a .jpg or .jpeg or .png

            String fileName = "profile_" + System.currentTimeMillis()+ "_" + image.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;
            File imageFile = new File(filePath);
            image.transferTo(imageFile.toPath());

            existingUser.setUserProfileImage(filePath);
            usersRepository.save(existingUser);
        }

        // package the data to return
        UserDto userDto = UserDto.builder()
                .email(existingUser.getUsername())
                .userProfileImage(existingUser.getUserProfileImage())
                .message("update success")
                .build();

        // The following are not returned as updates
        // - role
        // - token
        // - refreshToken
        // - expiration

        return userDto;
    }

}
