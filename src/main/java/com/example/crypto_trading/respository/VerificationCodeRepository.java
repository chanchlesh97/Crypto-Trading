package com.example.crypto_trading.respository;

import com.example.crypto_trading.domain.VerificationType;
import com.example.crypto_trading.modal.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, String> {
    public VerificationCode findByUserId(String userId);
}
