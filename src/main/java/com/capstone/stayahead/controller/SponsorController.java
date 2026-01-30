package com.capstone.stayahead.controller;


import com.capstone.stayahead.exception.ResourceNotFoundException;
import com.capstone.stayahead.model.Sponsor;
import com.capstone.stayahead.service.SponsorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stayahead")
public class SponsorController {

    @Autowired
    SponsorService sponsorService;

    //Create Sponsor
    @PostMapping("/admin/sponsor")
    public ResponseEntity<Object> save(@Valid @RequestBody Sponsor sponsor){
       sponsorService.save(sponsor);

       return new ResponseEntity<>("Sponsor Created", HttpStatus.CREATED) ;
    }

    // Update Sponsor
    @PutMapping("/admin/sponsor/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Integer id, @Valid @RequestBody Sponsor sponsor)
            throws ResourceNotFoundException {

        Sponsor _sponsor = sponsorService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        _sponsor.setName(sponsor.getName());
        _sponsor.setPhone(sponsor.getPhone());
        _sponsor.setAddress(sponsor.getAddress());
        _sponsor.setEmail(sponsor.getEmail());

        sponsorService.save(_sponsor);
        return new ResponseEntity<>("Sponsor updated", HttpStatus.OK); //201
    }
    //Retrieve ALl sponsors
    @GetMapping("/admin/sponsor/getall")
    public ResponseEntity<Object> all(Sponsor sponsor) throws ResourceNotFoundException{

        List<Sponsor> sponsorList = sponsorService.findAll();
        if(sponsorList.isEmpty()){
            throw new ResourceNotFoundException("Items not found.");
        }
        return new ResponseEntity<>(sponsorService.findAll(), HttpStatus.OK); //200

    }
    //Finding sponsor by id
    @GetMapping("/admin/sponsor/{id}")
    public ResponseEntity<Object> byId(@PathVariable("id") Integer id) throws ResourceNotFoundException{

        Sponsor _sponsor = sponsorService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        return new ResponseEntity<>(_sponsor, HttpStatus.OK) ; //200
    }

    // Find sponsor by name
    @GetMapping("/admin/sponsor/find")
    public ResponseEntity<Object> findByName(@RequestParam String name) throws ResourceNotFoundException{

        List<Sponsor> _sponsor = sponsorService.findByName(name);
             if (_sponsor.isEmpty())
                throw  new ResourceNotFoundException("Item not found");

        return new ResponseEntity<>(_sponsor, HttpStatus.OK) ; //200
    }



}
