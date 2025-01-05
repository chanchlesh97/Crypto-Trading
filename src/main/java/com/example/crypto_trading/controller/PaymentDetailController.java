package com.example.crypto_trading.controller;

import com.example.crypto_trading.modal.PaymentDetail;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.services.PaymentDetailService;
import com.example.crypto_trading.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment-details")
public class PaymentDetailController {
    @Autowired
    private PaymentDetailService paymentDetailService;

    @Autowired
    private UserService userService;


    @PostMapping("")
    public ResponseEntity<PaymentDetail> addPaymentDetails(
            @RequestHeader("Authorization") String jwt,
            @RequestBody PaymentDetail paymentDetail) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetail paymentDetail1 = paymentDetailService.addPaymentDetails(
                paymentDetail.getAccountNumber(),
                paymentDetail.getAccoundHolderName(),
                paymentDetail.getIfsc(),
                paymentDetail.getBankName(),
                user);
        return new ResponseEntity<>(paymentDetail1, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<PaymentDetail> getUserPaymentDetails(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentDetail paymentDetail = paymentDetailService.getUserPaymentDetails(user);
        return ResponseEntity.ok(paymentDetail);
    }

}
