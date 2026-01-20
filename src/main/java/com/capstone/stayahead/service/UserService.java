package com.capstone.stayahead.service;

import com.capstone.stayahead.model.Users;

import com.capstone.stayahead.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface{

    @Autowired
    UsersRepository usersRepository;


    @Override
    public void save(Users users) {
        usersRepository.save(users);
    }

    @Override
    public void deleteById(Integer id) {
        usersRepository.deleteById(id);
    }

    @Override
    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public Optional<Users> findById(Integer id) {
        return usersRepository.findById(id);
    }
}
