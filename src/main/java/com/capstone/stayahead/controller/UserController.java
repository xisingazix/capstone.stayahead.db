package com.capstone.stayahead.controller;

import com.capstone.stayahead.exception.ResourceNotFoundException;
import com.capstone.stayahead.model.Score;
import com.capstone.stayahead.model.Users;
import com.capstone.stayahead.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.logging.Handler;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("")
    public ResponseEntity<Object> save(@Valid @RequestBody Users users){
        userService.save(users);

        return new ResponseEntity<>("User created", HttpStatus.CREATED) ; //201
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Integer id, @RequestBody Users users)
                                        throws ResourceNotFoundException {
        try {
            Users _users = userService.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
            _users.setFirstName(users.getFirstName());
            _users.setLastName(users.getLastName());
            _users.setEmail(users.getEmail());

            userService.save(_users);
            return new ResponseEntity<>("User updated", HttpStatus.NO_CONTENT); //201
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    // End-point to View all users
    @GetMapping("")
    public ResponseEntity<Object> all(Users users) throws ResourceNotFoundException{
        try {
            List<Users> usersList = userService.findAll();
            if(usersList.isEmpty()){
                throw new ResourceNotFoundException("Items not found.");
            }
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK); //200
        }catch(ResourceNotFoundException e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }


    // End-point to View users by Id( path variable)
    // Global exception handling
    @GetMapping("/{id}")
    public ResponseEntity<Object> byId(@PathVariable("id") Integer id) throws ResourceNotFoundException{

        Users _users = userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        return new ResponseEntity<>(_users, HttpStatus.OK) ; //200
    }

}
