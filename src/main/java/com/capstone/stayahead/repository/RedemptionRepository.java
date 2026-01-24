package com.capstone.stayahead.repository;

import com.capstone.stayahead.model.Redemption;
import com.capstone.stayahead.model.RedemptionId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public interface RedemptionRepository extends JpaRepository<Redemption, RedemptionId> {

    public List<Redemption> findByUsersId(Integer userid);
    public List<Redemption> findByVoucherId(Integer voucherid);

    // Custom Query for OptionalIds
    @Query("SELECT r FROM Redemption r WHERE " +
            "(:uId IS NULL OR r.redemptionId.usersId = :uId) AND " +
            "(:vId IS NULL OR r.redemptionId.voucherId = :vId)")
    List<Redemption> findByCustomCriteria(@Param("uId") Integer uId, @Param("vId")Integer vId);

}
