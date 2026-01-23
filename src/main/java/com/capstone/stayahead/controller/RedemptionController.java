package com.capstone.stayahead.controller;

import com.capstone.stayahead.service.UserService;
import com.capstone.stayahead.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redemption")
public class RedemptionController {

    @Autowired
    UserService userService;

    @Autowired
    VoucherService voucherService;



}
