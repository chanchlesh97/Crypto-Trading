package com.example.crypto_trading.services;

import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.request.Withdrawal;

import java.util.List;

public interface WithdrawalService {
    Withdrawal requestWithdrawal(Long amount, User user);

    Withdrawal proceedWithdrawal(Long withdrawalId, boolean accept);

    List<Withdrawal> getUserWithdrawals(User user);
    List<Withdrawal> getAllWithdrawalRequests();
}
