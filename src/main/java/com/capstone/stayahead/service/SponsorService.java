package com.capstone.stayahead.service;

import com.capstone.stayahead.model.Sponsor;
import com.capstone.stayahead.repository.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SponsorService implements  SponsorServiceInterface{

    @Autowired
    SponsorRepository sponsorRepository;


    @Override
    public void save(Sponsor sponsor) {
         sponsorRepository.save(sponsor);
    }

    @Override
    public void deleteById(Integer id) {
        sponsorRepository.deleteById(id);
    }

    @Override
    public List<Sponsor> findAll() {
        return sponsorRepository.findAll();
    }

    @Override
    public Optional<Sponsor> findById(Integer id) {
        return sponsorRepository.findById(id);
    }

    public List<Sponsor> findByName(String name){
        return sponsorRepository.findByName(name);
    }
}
