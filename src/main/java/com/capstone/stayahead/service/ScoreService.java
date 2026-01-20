package com.capstone.stayahead.service;

import com.capstone.stayahead.model.Score;
import com.capstone.stayahead.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreService implements ScoreServiceInterface{

    @Autowired
    ScoreRepository scoreRepository;


    @Override
    public void save(Score score) {
        scoreRepository.save(score);
    }

    @Override
    public void deleteById(Integer id) {
        scoreRepository.deleteById(id);
    }

    @Override
    public List<Score> findAll() {
        Sort sort = Sort.by("score").descending()
                .and(Sort.by("updated_at")).ascending();
        return scoreRepository.findAll();
    }

    @Override
    public Optional<Score> findById(Integer id) {
        return scoreRepository.findById(id);
    }

    public List<Score> getTopFiveHighScore (){
        return scoreRepository.findTop5ByOrderByScoreDescUpdatedAtAsc();
    }

}
