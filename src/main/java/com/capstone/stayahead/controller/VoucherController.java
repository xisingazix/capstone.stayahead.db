package com.capstone.stayahead.controller;

import com.capstone.stayahead.exception.ResourceNotFoundException;
import com.capstone.stayahead.model.Score;
import com.capstone.stayahead.model.Sponsor;
import com.capstone.stayahead.model.Users;
import com.capstone.stayahead.model.Voucher;
import com.capstone.stayahead.service.SponsorService;
import com.capstone.stayahead.service.VoucherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    VoucherService voucherService;

    @Autowired
    SponsorService sponsorService;

    // Create Voucher
    @PostMapping("")
    public ResponseEntity<Object> save(@Valid @RequestBody Voucher voucher) throws ResourceNotFoundException{
       Optional<Sponsor> sponsor = sponsorService.findById(voucher.getSponsor().getId());
        if (sponsor.isEmpty()){
            throw new ResourceNotFoundException("Required input is not valid");
        }
        voucherService.save(voucher);
        return new ResponseEntity<>( "Voucher Created", HttpStatus.ACCEPTED) ;
    }

    // Update Voucher
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Integer id,@Valid @RequestBody Voucher voucher)
            throws ResourceNotFoundException {

        Voucher _voucher = voucherService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        _voucher.setSponsor(voucher.getSponsor());
        _voucher.setName(voucher.getName());
        _voucher.setDescription(voucher.getDescription());
        _voucher.setTerms(voucher.getTerms());

        voucherService.save(_voucher);
        return new ResponseEntity<>(_voucher, HttpStatus.OK); //201
    }
    // View all voucher
    @GetMapping("")
    public ResponseEntity<Object> all() throws ResourceNotFoundException {
        List<Voucher> voucherList =  voucherService.findAll();
        if(  voucherList.isEmpty())
            throw new ResourceNotFoundException("Items not found");
        return new ResponseEntity<>( voucherList, HttpStatus.OK) ; //200
    }
    // view voucher by voucher id
    @GetMapping("/{id}")
    public ResponseEntity<Object> byId(@PathVariable("id") Integer id) throws ResourceNotFoundException{

        Voucher _voucher = voucherService.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Item not found"));
        return new ResponseEntity<>(_voucher, HttpStatus.OK) ; //200
    }
    // view voucher by sponsor id
    @GetMapping("/sponsor{id}")
    public ResponseEntity<Object> bySponsorId(@PathVariable("id") Integer id) throws ResourceNotFoundException{

        List<Voucher> _voucher = voucherService.findBySponsorId(id);
           if( _voucher.isEmpty())
               throw new ResourceNotFoundException("Item not found");
        return new ResponseEntity<>(_voucher, HttpStatus.OK) ; //200
    }


}
