package com.capstone.stayahead.service;

import com.capstone.stayahead.model.Redemption;
import com.capstone.stayahead.model.RedemptionId;
import com.capstone.stayahead.model.Score;

import java.util.List;
import java.util.Optional;

public interface RedemptionServiceInterface {

    public void save(Redemption redemption);

    public void deleteById(RedemptionId redemptionId);

    public List<Redemption> findAll();

    public Optional<Redemption> findById(RedemptionId redemptionId);

}
