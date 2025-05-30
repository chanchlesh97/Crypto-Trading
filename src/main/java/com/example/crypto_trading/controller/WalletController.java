package com.example.crypto_trading.controller;

import com.example.crypto_trading.modal.Order;
import com.example.crypto_trading.modal.PaymentOrder;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.modal.Wallet;
import com.example.crypto_trading.request.WalletTransactionRequest;
import com.example.crypto_trading.response.PaymentResponse;
import com.example.crypto_trading.services.OrderService;
import com.example.crypto_trading.services.PaymentService;
import com.example.crypto_trading.services.UserService;
import com.example.crypto_trading.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;


    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long walletId,
            @RequestBody WalletTransactionRequest walletTransactionRequest) throws Exception {
        User senderUser = userService.findUserProfileByJwt(jwt);

        Wallet receiverWallet = walletService.findWalletById(walletId);

        Wallet wallet = walletService.walletToWalletTransfer(
                senderUser,
                receiverWallet,
                walletTransactionRequest.getAmount());

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId
//            @RequestBody WalletTransactionRequest walletTransactionRequest
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.getOrderById(orderId);

        Wallet wallet = walletService.payOrderPayment(order, user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/deposit")
    public ResponseEntity<Wallet> addMoneyToWallet(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(name ="order_id") Long orderId,
            @RequestParam(name="payment_id") String paymentId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);


        Wallet wallet = walletService.getUserWallet(user);
        PaymentOrder order = paymentService.getPaymentOrderById(orderId);
        Boolean status = paymentService.proceedPaymentOrder(order, paymentId);
        if(status) {
            wallet = walletService.addBalance(wallet, order.getAmount());
        }

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }


}
