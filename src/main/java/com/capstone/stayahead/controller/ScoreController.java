package com.capstone.stayahead.controller;

import com.capstone.stayahead.exception.ResourceNotFoundException;
import com.capstone.stayahead.model.Score;
import com.capstone.stayahead.model.Users;
import com.capstone.stayahead.service.ScoreService;
import com.capstone.stayahead.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stayahead")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @Autowired
    UserService userService;


    // Local exception handling
    // Edit Personal score
    @PutMapping("/user/score")
    public ResponseEntity<Object> update(
            @RequestParam("email") String email,
            @RequestParam("score") String score)throws ResourceNotFoundException{

        // Ensure user exists first, save compare score
        // Check customer exists
        Users users = userService.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not registered"));

        Map<String, String> response = new HashMap<>();

        Score _score = scoreService.findById(users.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            // Check if Score >  personal currentHighScore
            if ( _score.getScore() < Integer.parseInt(score)) {
                _score.setScore(Integer.parseInt(score));
                scoreService.save(_score);

                response.put("message", "You have achieved a personal High score.");
                return new ResponseEntity<>( response, HttpStatus.OK);
            }

            response.put("message", "No new high score set. Please try again.");
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // End-point to View all score
    // Global exception handling
    @GetMapping("/public/score")
    public ResponseEntity<Object> all() throws ResourceNotFoundException {
        List<Score> scoreList =  scoreService.findAll();
        if( scoreList.isEmpty())
            throw new ResourceNotFoundException("Items not found");
        return new ResponseEntity<>(scoreList, HttpStatus.OK) ; //200
    }
    // End-point to view Leaderboard
    @GetMapping("/public/top5")
    public ResponseEntity<Object> topFiveScore()throws ResourceNotFoundException{

        List<Users> users = userService.findAll();
        List<Score> scoreList =  scoreService.getTopFiveHighScore();

        Map<String, Integer> responseTop5 = new HashMap<>();

        scoreList.forEach(scoreItem ->{
            for (Users user : users) {
                if(user.getId().equals(scoreItem.getId())){
                    responseTop5.put(user.getEmail(), scoreItem.getScore());
                }
            }
        });

        return new ResponseEntity<>(responseTop5, HttpStatus.OK) ;     //200
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
