package com.example.crypto_trading.respository;

import com.example.crypto_trading.modal.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {
    PaymentDetail findByUserId(Long userId);
}
