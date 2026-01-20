package com.capstone.stayahead.controller;

import com.capstone.stayahead.model.Score;
import com.capstone.stayahead.model.Users;
import com.capstone.stayahead.service.ScoreService;
import com.capstone.stayahead.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @Autowired
    UserService userService;

    // No postmapping, do not want scorecontroller to create userid

    // Edit Personal score
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") Integer usersId,
            @RequestBody Score score)throws Exception{
        // Ensure user exists first, save compare score
        try {
            // Check customer exists
            Score _score = scoreService.findById(usersId)
                                    .orElseThrow(()-> new Exception("User not found")); // for cases customer not exists
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
    @GetMapping("")
    public ResponseEntity<Object> all(Users users){

        return new ResponseEntity<>(scoreService.findAll(), HttpStatus.OK) ; //200
    }

    @GetMapping("/top5")
    public ResponseEntity<Object> topFiveScore(Users users){

        return new ResponseEntity<>(scoreService.getTopFiveHighScore(), HttpStatus.OK) ; //200
    }
    // End-point to View  score of users by Id( path variable)
    @GetMapping("/{id}")
    public ResponseEntity<Object> byId(@PathVariable("id") Integer id){
        return new ResponseEntity<>(scoreService.findById(id), HttpStatus.OK) ; //200
    }

}
