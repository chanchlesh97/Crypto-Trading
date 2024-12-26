package com.example.crypto_trading.services;

import com.example.crypto_trading.domain.VerificationType;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.modal.VerificationCode;
import org.springframework.stereotype.Service;

@Service
public interface VerificationCodeService {

    public VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    public VerificationCode getVerificationCodeById(String id) throws Exception;

    public VerificationCode getVerificationCodeByUser(User user);

    public void deleteVerificationCode(VerificationCode verificationCode);
}
