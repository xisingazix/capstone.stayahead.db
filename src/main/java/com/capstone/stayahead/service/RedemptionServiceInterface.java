package com.capstone.stayahead.service;

import com.capstone.stayahead.model.Redemption;
import com.capstone.stayahead.model.RedemptionId;


import java.util.List;
import java.util.Optional;

public interface RedemptionServiceInterface {

    public Redemption save(Redemption redemption);

    public void deleteById(RedemptionId redemptionId);

    public List<Redemption> findAll();

    public Optional<Redemption> findById(RedemptionId redemptionId);

}
