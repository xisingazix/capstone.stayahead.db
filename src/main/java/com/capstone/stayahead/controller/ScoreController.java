package com.capstone.stayahead.controller;

import com.capstone.stayahead.exception.ResourceNotFoundException;
import com.capstone.stayahead.model.Score;
import com.capstone.stayahead.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stayahead")
public class ScoreController {

    @Autowired
    ScoreService scoreService;


    // Local exception handling
    // Edit Personal score
    @PutMapping("/user/{id}/score")
    public ResponseEntity<Object> update(
            @PathVariable("id") Integer usersId,
            @RequestBody Score score)throws ResourceNotFoundException{
        // Ensure user exists first, save compare score
            // Check customer exists
        Score _score = scoreService.findById(usersId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            // Check if Score >  personal currentHighScore
            if ( _score.getScore() < score.getScore()) {
                _score.setScore(score.getScore());
                scoreService.save(_score);
                return new ResponseEntity<>( "Score Updated", HttpStatus.OK);
            }
            return new ResponseEntity<>("No New HighScore set ,Please try again", HttpStatus.OK);
    }

    // End-point to View all score
    // Global exception handling
    @GetMapping("public/score")
    public ResponseEntity<Object> all() throws ResourceNotFoundException {
        List<Score> scoreList =  scoreService.findAll();
        if( scoreList.isEmpty())
            throw new ResourceNotFoundException("Items not found");
        return new ResponseEntity<>(scoreList, HttpStatus.OK) ; //200
    }
    // End-point to view Leaderboard
    @GetMapping("public/top5")
    public ResponseEntity<Object> topFiveScore()throws ResourceNotFoundException{
        List<Score> scoreList =  scoreService.getTopFiveHighScore();
        if( scoreList.isEmpty())
            throw new ResourceNotFoundException("Items not found");
        return new ResponseEntity<>(scoreList, HttpStatus.OK) ; //200
    }

    // End-point to View  score of users by Id( path variable)
    @GetMapping("/public/{id}/score")
    public ResponseEntity<Object> byId(@PathVariable("id") Integer id)
            throws ResourceNotFoundException{

        Score _score = scoreService.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Item not found"));
        return new ResponseEntity<>(_score, HttpStatus.OK) ; //200
    }

}
