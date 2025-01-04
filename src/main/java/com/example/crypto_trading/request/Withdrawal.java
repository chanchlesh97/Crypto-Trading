package com.example.crypto_trading.request;

import com.example.crypto_trading.domain.WithdrawalStatus;
import com.example.crypto_trading.modal.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private WithdrawalStatus status;

    private Long amount;
    @ManyToOne
    private User user;

    private LocalDateTime date = LocalDateTime.now();
}
