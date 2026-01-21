package com.capstone.stayahead.controller;

import com.capstone.stayahead.exception.ResourceNotFoundException;
import com.capstone.stayahead.model.Score;
import com.capstone.stayahead.model.Users;
import com.capstone.stayahead.service.ScoreService;
import com.capstone.stayahead.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @Autowired
    UserService userService;

    // No postmapping, do not want scorecontroller to create userid

    // Local exception handling
    // Edit Personal score
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") Integer usersId,
            @RequestBody Score score)throws ResourceNotFoundException{
        // Ensure user exists first, save compare score
        try {
            // Check customer exists
            Score _score = scoreService.findById(usersId)
                                    .orElseThrow(()-> new ResourceNotFoundException("Item not found")); // for cases customer not exists
            // Check if Score >  personal currentHighScore
            if ( _score.getScore() < score.getScore()) {
                _score.setScore(score.getScore());
                scoreService.save(_score);
                return new ResponseEntity<>("New HighScore updated", HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>("No New HighScore set ,Please try again", HttpStatus.NO_CONTENT);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // End-point to View all score
    // Global exception handling
    @GetMapping("")
    public ResponseEntity<Object> all() throws ResourceNotFoundException {
        List<Score> scoreList =  scoreService.findAll();
        if( scoreList.isEmpty())
            throw new ResourceNotFoundException("Items not found");
        return new ResponseEntity<>(scoreList, HttpStatus.OK) ; //200
    }
    // End-point to view Leaderboard
    // Global exception handling
    @GetMapping("/top5")
    public ResponseEntity<Object> topFiveScore()throws ResourceNotFoundException{
        List<Score> scoreList =  scoreService.getTopFiveHighScore();
        if( scoreList.isEmpty())
            throw new ResourceNotFoundException("Items not found");
        return new ResponseEntity<>(scoreList, HttpStatus.OK) ; //200
    }

    // End-point to View  score of users by Id( path variable)
    // Global exception handling
    @GetMapping("/{id}")
    public ResponseEntity<Object> byId(@PathVariable("id") @NotNull Integer id) throws ResourceNotFoundException{

        Score _score = scoreService.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Item not found"));
        return new ResponseEntity<>(_score, HttpStatus.OK) ; //200
    }

}
