package com.example.crypto_trading.controller;

import com.example.crypto_trading.domain.USER_ROLE;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.modal.Wallet;
import com.example.crypto_trading.request.Withdrawal;
import com.example.crypto_trading.services.UserService;
import com.example.crypto_trading.services.WalletService;
import com.example.crypto_trading.services.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {
    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;

    @PostMapping("/{amount}")
    public ResponseEntity<?> requestWithdrawal(@RequestHeader("Authorization") String jwt, @PathVariable Long amount) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
        Wallet wallet = walletService.getUserWallet(user);
        walletService.addBalance(wallet, -withdrawal.getAmount());
        return new ResponseEntity<>(withdrawal, HttpStatus.CREATED);
    }

    @PostMapping("/{withdrawalId}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(@RequestHeader("Authorization") String jwt, @PathVariable Long withdrawalId, @PathVariable boolean accept) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Withdrawal withdrawal = withdrawalService.proceedWithdrawal(withdrawalId, accept);
        Wallet wallet = walletService.getUserWallet(user);
        if(!accept){
            walletService.addBalance(wallet, withdrawal.getAmount());
        }
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Withdrawal> withdrawals = withdrawalService.getUserWithdrawals(user);
        return new ResponseEntity<>(withdrawals, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequests(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        if(user.getRole() == USER_ROLE.ROLE_ADMIN){
            throw new Exception("You are not authorized to view this page");
        }
        List<Withdrawal> withdrawals = withdrawalService.getAllWithdrawalRequests();
        return new ResponseEntity<>(withdrawals, HttpStatus.OK);
    }
}
