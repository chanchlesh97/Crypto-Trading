package com.example.crypto_trading.respository;

import com.example.crypto_trading.modal.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, String> {
     ForgotPasswordToken findByUserId(String userId);
}
