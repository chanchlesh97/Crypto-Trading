package com.example.crypto_trading.services;

import com.example.crypto_trading.domain.VerificationType;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.modal.VerificationCode;
import org.springframework.stereotype.Service;

@Service
public interface VerificationCodeService {

     VerificationCode sendVerificationCode(User user, VerificationType verificationType);

     VerificationCode getVerificationCodeById(String id) throws Exception;

     VerificationCode getVerificationCodeByUser(User user);

     void deleteVerificationCode(VerificationCode verificationCode);
}
