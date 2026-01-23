package com.capstone.stayahead.repository;

import com.capstone.stayahead.model.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SponsorRepository  extends JpaRepository<Sponsor, Integer> {

    List<Sponsor> findByName(String name);


}
