package com.capstone.stayahead.service;

import com.capstone.stayahead.model.Redemption;
import com.capstone.stayahead.repository.RedemptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedemptionService implements RedemptionServiceInterface{

    @Autowired
    RedemptionRepository redemptionRepository;

    @Override
    public void save(Redemption redemption) {
        redemptionRepository.save(redemption);
    }

    @Override
    public void deleteById(Integer id) {
        redemptionRepository.deleteById(id);
    }

    @Override
    public List<Redemption> findAll() {
        return redemptionRepository.findAll();
    }

    @Override
    public Optional<Redemption> findById(Integer id) {
        return redemptionRepository.findById(id);
    }
}
