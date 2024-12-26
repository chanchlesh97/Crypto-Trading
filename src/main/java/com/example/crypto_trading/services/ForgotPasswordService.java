package com.example.crypto_trading.services;

import com.example.crypto_trading.domain.VerificationType;
import com.example.crypto_trading.modal.ForgotPasswordToken;
import com.example.crypto_trading.modal.User;
import org.springframework.stereotype.Service;

@Service
public interface ForgotPasswordService {
    public ForgotPasswordToken createForgotPasswordToken(User user,
                                                         String id,
                                                         String otp,
                                                         VerificationType verificationType,
                                                         String sendTo);
    public ForgotPasswordToken findForgotPasswordTokenByUser(User user);
    public ForgotPasswordToken findForgotPasswordTokenById(String id);
    public void deleteForgotPasswordToken(ForgotPasswordToken forgotPasswordToken);
}
