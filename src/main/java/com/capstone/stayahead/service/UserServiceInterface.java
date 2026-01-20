package com.capstone.stayahead.service;

import com.capstone.stayahead.model.Users;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    public void save(Users users);

    public void deleteById(Integer id);

    public List<Users> findAll();

    public Optional<Users> findById(Integer id);

}
