package com.capstone.stayahead.repository;

import com.capstone.stayahead.model.Score;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {


    @Modifying           // essential for custom queries that delete or update records
    @Transactional      // essential to ensure that the operation is within one transaction
    List<Score> findTop5ByOrderByScoreDescUpdatedAtAsc();

}
