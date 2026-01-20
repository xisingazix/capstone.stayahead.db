package com.capstone.stayahead.controller;

import com.capstone.stayahead.model.Score;
import com.capstone.stayahead.model.Users;
import com.capstone.stayahead.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Handler;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("")
    public ResponseEntity<Object> save(@RequestBody Users users){
        userService.save(users);

        return new ResponseEntity<>("User created", HttpStatus.CREATED) ; //201
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id")Integer id, @RequestBody Users users){
        users.setId(id);
        userService.save(users);

        return new ResponseEntity<>("User updated", HttpStatus.NO_CONTENT) ; //201
    }

    // End-point to View all users
    @GetMapping("")
    public ResponseEntity<Object> all(Users users){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK) ; //200
    }

    // End-point to View users by Id( path variable)
    @GetMapping("/{id}")
    public ResponseEntity<Object> byId(@PathVariable("id") Integer id){
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK) ; //200
    }


}
