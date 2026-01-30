package com.capstone.stayahead.service;

import com.capstone.stayahead.model.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherServiceInterface {

    public void save(Voucher voucher);

    public void deleteById(Integer id);

    public List<Voucher> findAll();

    public Optional<Voucher> findById(Integer id);
}
