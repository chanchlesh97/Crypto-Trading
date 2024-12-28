package com.example.crypto_trading.services;

import com.example.crypto_trading.modal.Order;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.modal.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long money);

    Wallet findWalletById(Long id);

    Wallet walletToWalletTransfer(User sender, Wallet toWallet, Long money);

    Wallet payOrderPayment(Order order, User user);
}
