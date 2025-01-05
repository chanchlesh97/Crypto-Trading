package com.example.crypto_trading.controller;

import com.example.crypto_trading.domain.PaymentMethod;
import com.example.crypto_trading.modal.PaymentOrder;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.response.PaymentResponse;
import com.example.crypto_trading.services.PaymentService;
import com.example.crypto_trading.services.UserService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @PostMapping("/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount
    ) throws Exception, RazorpayException, StripeException {
        User user = userService.findUserProfileByJwt(jwt);
        PaymentOrder paymentOrder = paymentService.createPaymentOrder(user, amount, paymentMethod);
        PaymentResponse paymentResponse;

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse = paymentService.createRazorpayPaymentLink(user, amount);
        } else{
            paymentResponse = paymentService.createStripePaymentLink(user, amount, paymentOrder.getId());
        }

        return new ResponseEntity<>(paymentResponse,  HttpStatus.CREATED);
    }
}
