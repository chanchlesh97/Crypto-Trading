package com.example.crypto_trading.request;

import com.example.crypto_trading.domain.WalletTransactionType;
import com.example.crypto_trading.modal.Wallet;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class WalletTransactionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Wallet wallet;

    private WalletTransactionType transactionType;

    private LocalDate date;

    private String transferId;

    private Long amount;
}
