package com.capstone.stayahead.controller;
import com.capstone.stayahead.exception.ResourceNotFoundException;
import com.capstone.stayahead.model.*;
import com.capstone.stayahead.service.RedemptionService;
import com.capstone.stayahead.service.UserService;
import com.capstone.stayahead.service.VoucherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/stayahead")
public class RedemptionController {

    @Autowired
    VoucherService voucherService;

    @Autowired
    UserService userService;

    @Autowired
    RedemptionService redemptionService;

    @PostMapping("/user/redemption")
    public ResponseEntity<Object> save(@Valid @RequestBody RedemptionId redemptionId)
                                        throws ResourceNotFoundException {
        // Check for Null Entry
        if(redemptionId.getUsersId() == null || redemptionId.getVoucherId() == null )
            throw new ResourceNotFoundException("Missing Entry");
        // Check User table for user
        Users users = userService.findById(redemptionId.getUsersId())
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        // Check Voucher table for voucher
        Voucher voucher = voucherService.findById(redemptionId.getVoucherId())
                .orElseThrow(()->new ResourceNotFoundException("Voucher not found"));
        // Check Redemption table if redeemed
        if(redemptionService.findById(redemptionId).isPresent())
            throw new ResourceNotFoundException("Voucher has been redeemed");
        // Redeem voucher
        Redemption redemption = new Redemption(redemptionId, voucher, users );
        redemptionService.save(redemption);
        return new ResponseEntity<>("Redemption Complete", HttpStatus.OK);
    }
    @DeleteMapping("/admin/redemption")
    public ResponseEntity<Object> deleteById(@Valid @RequestBody RedemptionId redemptionId)
                                    throws ResourceNotFoundException{
            Redemption redemption = redemptionService.findById(redemptionId)
                                            .orElseThrow(()-> new ResourceNotFoundException("Voucher not found"));
            redemptionService.deleteById(redemptionId);
        return new ResponseEntity<>("Redemption Deleted", HttpStatus.OK);

    }
    // Not suppose to change primary key of a composite key,
    // TODO: KIV what to do for PUTMapping
//    @PutMapping("{id}")
//    public ResponseEntity<Object> updateReede(@Valid @PathVariable("id") Users userid,
//                                               @Valid @RequestBody RedemptionId redemptionId)
//                                        throws ResourceNotFoundException {
//        // Missing Entry check
//        if(redemptionId.getUsersId() == null || redemptionId.getVoucherId() == null )
//            throw new ResourceNotFoundException("Missing Entry");
//        // check if redeemed ( in order to "update")
//        Redemption redemption  =  redemptionService.findById(redemptionId)
//                    .orElseThrow(()->new ResourceNotFoundException("Item not found"));
//        // Update RedemptionId and userid in redemption
//        RedemptionId _redemptionId = new RedemptionId(userid.getId(), redemptionId.getVoucherId());
//        redemption.setRedemptionId(_redemptionId);
//        redemption.setUsers(userid);
//
//        redemptionService.save(redemption);
//        return new ResponseEntity<>("Redemption Updated", HttpStatus.OK);
//        }
//    }
    @GetMapping("/admin/redemption/search")
    public ResponseEntity<List<Redemption>> findById(@RequestParam(value ="userid", required = false ) Integer userid ,
                                            @RequestParam(value = "voucherid", required = false ) Integer voucherid ) {

        List<Redemption> redemptions = redemptionService.findByOptionalIds(userid, voucherid);

        return new ResponseEntity<>(redemptions, HttpStatus.OK);
    }

}
