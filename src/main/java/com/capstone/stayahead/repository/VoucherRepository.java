package com.capstone.stayahead.repository;

import com.capstone.stayahead.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer > {
    List<Voucher> findBySponsorId (Integer id);
}
