package com.capstone.stayahead.service;

import com.capstone.stayahead.exception.ResourceNotFoundException;
import com.capstone.stayahead.model.Redemption;
import com.capstone.stayahead.model.RedemptionId;
import com.capstone.stayahead.model.Users;
import com.capstone.stayahead.model.Voucher;
import com.capstone.stayahead.repository.RedemptionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedemptionService implements RedemptionServiceInterface{

    @Autowired
    RedemptionRepository redemptionRepository;

    @Autowired
    UserService userService;

    @Autowired
    VoucherService voucherService;


    @Override
    public void save(Redemption redemption) {
        redemptionRepository.save(redemption);
    }

    @Override
    public void deleteById(RedemptionId redemptionId) {
        redemptionRepository.deleteById(redemptionId);
    }

    @Override
    public List<Redemption> findAll() {
        return redemptionRepository.findAll();
    }

    @Override
    public Optional<Redemption> findById(RedemptionId redemptionId) {
        return redemptionRepository.findById(redemptionId);
    }


    public List<Redemption> findByUserId(Users users) {
        return redemptionRepository.findByUsersId(users.getId());
    }
    public List<Redemption> findByVoucherId(Voucher voucher) {
        return redemptionRepository.findByVoucherId(voucher.getId());
    }
    public void usersIdAndVoucherIdCheck(RedemptionId redemptionId) throws ResourceNotFoundException{
        if (redemptionId.getUsersId() == null || userService.findById(redemptionId.getUsersId()).isEmpty())
            throw new ResourceNotFoundException("User not found");
        if (redemptionId.getVoucherId() == null || voucherService.findById(redemptionId.getVoucherId()).isEmpty())
            throw new ResourceNotFoundException("Voucher not found");
    }
    public List<Redemption> findByOptionalIds ( Integer userId , Integer voucherId){
        return redemptionRepository.findByCustomCriteria(userId, voucherId);
    }
}
