package com.example.crypto_trading.services;

import com.example.crypto_trading.modal.TwoFactorOTP;
import com.example.crypto_trading.modal.User;
import org.springframework.stereotype.Service;

@Service
public interface TwoFactorOTPService {

    TwoFactorOTP createTwoFactorOTP(User user, String otp, String jwt);

    TwoFactorOTP getTwoFactorOTP(User user );

    TwoFactorOTP findByUser(Long userId);
    TwoFactorOTP findById(String id);

    boolean verifyTwoFactorOTP(TwoFactorOTP twoFactorOTP, String otp);

    void deleteTwoFactorOTP(TwoFactorOTP twoFactorOTP);
}
