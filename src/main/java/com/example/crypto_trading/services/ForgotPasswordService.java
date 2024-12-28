package com.example.crypto_trading.services;

import com.example.crypto_trading.domain.VerificationType;
import com.example.crypto_trading.modal.ForgotPasswordToken;
import com.example.crypto_trading.modal.User;
import org.springframework.stereotype.Service;

@Service
public interface ForgotPasswordService {
     ForgotPasswordToken createForgotPasswordToken(User user,
                                                         String id,
                                                         String otp,
                                                         VerificationType verificationType,
                                                         String sendTo);
     ForgotPasswordToken findForgotPasswordTokenByUser(User user);
     ForgotPasswordToken findForgotPasswordTokenById(Long id);
     void deleteForgotPasswordToken(ForgotPasswordToken forgotPasswordToken);
}
