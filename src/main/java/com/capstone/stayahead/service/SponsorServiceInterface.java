package com.capstone.stayahead.service;

import com.capstone.stayahead.model.Sponsor;

import java.util.List;
import java.util.Optional;

public interface SponsorServiceInterface {

    public void save(Sponsor sponsor);

    public  void deleteById(Integer id);

    public List<Sponsor> findAll();

    public Optional<Sponsor> findById(Integer id);
}
