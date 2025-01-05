package com.example.crypto_trading.respository;

import com.example.crypto_trading.modal.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
//    PaymentOrder findByUserId(Long userId);
}
