package com.capstone.stayahead.service;

import com.capstone.stayahead.model.Score;

import java.util.List;
import java.util.Optional;

public interface ScoreServiceInterface {

    public void save(Score score);

    public void deleteById(Integer id);

    public List<Score> findAll();

    public Optional<Score> findById(Integer id);

}
