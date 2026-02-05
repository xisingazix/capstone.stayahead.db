package com.capstone.stayahead.controller;

import com.capstone.stayahead.dto.UserDto;
import com.capstone.stayahead.exception.EmailAlreadyExistsException;
import com.capstone.stayahead.exception.ResourceNotFoundException;
import com.capstone.stayahead.model.Score;
import com.capstone.stayahead.model.Users;

import com.capstone.stayahead.service.AuthService;
import com.capstone.stayahead.service.ScoreService;
import com.capstone.stayahead.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/stayahead")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    ScoreService scoreService;

    @PostMapping("/public/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody Users users) throws EmailAlreadyExistsException {
        Users _users = authService.signUp(users);

        return new ResponseEntity<>(_users, HttpStatus.CREATED) ;
    }

    @PostMapping("/public/signin")  // creating a public endpoint for signins
    public ResponseEntity<Object> signin(@RequestBody Users users){

        return new ResponseEntity<>(authService.signIn(users), HttpStatus.CREATED);
    }

    @PutMapping("/user/update")  // creating a public endpoint for signins, user authenticated end point for updating, require token
    public ResponseEntity<Object> update(
            @RequestParam("data") String data,           // "{"username": "JohnDoe"}"
            @Nullable @RequestParam(value = "image" , required = false) MultipartFile image)  // Depends on token input to identify who is requesting an update
            throws IOException, ResourceNotFoundException {               // IOException handles image issues (File image exception , object matter exception)
        // Convert "data" stored as a string into User object
        ObjectMapper objectMapper = new ObjectMapper();
        Users users = objectMapper.readValue(data, Users.class);

        return new ResponseEntity<>(authService.update(users , image), HttpStatus.OK);
    }


    // End-point to View all users ,require token
    @GetMapping("/getall")
    public ResponseEntity<Object> all(Users users) throws ResourceNotFoundException{

            List<Users> usersList = userService.findAll();
            if(usersList.isEmpty()){
                throw new ResourceNotFoundException("Items not found.");
            }
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK); //200
    }
    // End-point to View users by Id( path variable)
    // Global exception handling
    @GetMapping("/{id}")
    public ResponseEntity<Object> byId(@PathVariable("id") Integer id) throws ResourceNotFoundException{

        Users _users = userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        return new ResponseEntity<>(_users, HttpStatus.OK) ; //200
    }
    @GetMapping("/user/email")
    public ResponseEntity<Object> byEmail(@RequestParam String email) throws ResourceNotFoundException{

        Users existingUser = userService.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        UserDto userDto = UserDto.builder()
                .userid(existingUser.getId())
                .email(existingUser.getUsername())
                .score(existingUser.getScore().getScore())
                .userProfileImage(existingUser.getUserProfileImage())
                .message("retrieval")
                .build();

        return new ResponseEntity<>(userDto, HttpStatus.OK) ; //200
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") Integer id) throws ResourceNotFoundException{

        Users _users = userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        userService.deleteById(id);
        return new ResponseEntity<>(_users, HttpStatus.OK) ; //200
    }

}
