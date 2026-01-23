package com.capstone.stayahead.repository;

import com.capstone.stayahead.model.Redemption;
import com.capstone.stayahead.model.RedemptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public interface RedemptionRepository extends JpaRepository<Redemption, RedemptionId> {

    public Optional<Redemption> findByUsersIdAndVoucherId(Integer userid,Integer  voucherId);


}
