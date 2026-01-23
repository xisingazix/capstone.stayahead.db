package com.capstone.stayahead.controller;

import com.capstone.stayahead.exception.ResourceNotFoundException;
import com.capstone.stayahead.model.*;

import com.capstone.stayahead.service.RedemptionService;
import com.capstone.stayahead.service.UserService;
import com.capstone.stayahead.service.VoucherService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/redemption")
public class RedemptionController {

    @Autowired
    UserService userService;

    @Autowired
    VoucherService voucherService;

    @Autowired
    RedemptionService redemptionService;

    @PostMapping("")
    public ResponseEntity<Object> save(@Valid @RequestBody RedemptionId redemptionId)
                                        throws ResourceNotFoundException {
        if (redemptionId.getUsersId() == null || userService.findById(redemptionId.getUsersId()).isEmpty())
            throw new ResourceNotFoundException("UserId not found");
        if (redemptionId.getVoucherId() == null || voucherService.findById(redemptionId.getVoucherId()).isEmpty())
            throw new ResourceNotFoundException("VoucherId not found");
        Optional<Redemption> redeemed = redemptionService.findByRedemptionId(redemptionId);
        if(redeemed.isPresent())throw  new ResourceNotFoundException("Voucher has been redeemed");

        Redemption redemption = new Redemption(redemptionId);
        redemption.setRedemptionId(redemptionId);
        redemption.setUsers(redemption.getUsers());
        redemption.setVoucher(redemption.getVoucher());
        redemptionService.save(redemption);
        return new ResponseEntity<>("Redemption Complete", HttpStatus.OK);

    }

}
