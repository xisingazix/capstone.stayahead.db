package com.capstone.stayahead.service;

import com.capstone.stayahead.model.Voucher;
import com.capstone.stayahead.repository.SponsorRepository;
import com.capstone.stayahead.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherService implements VoucherServiceInterface {

    @Autowired
    VoucherRepository voucherRepository;


    @Override
    public void save(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    @Override
    public void deleteById(Integer id) {
        voucherRepository.deleteById(id);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Optional<Voucher> findById(Integer id) {
        return voucherRepository.findById(id);
    }

    public List<Voucher> findBySponsorId(Integer id){
        return voucherRepository.findBySponsorId(id);
    }
}
