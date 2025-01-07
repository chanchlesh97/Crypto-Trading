package com.example.crypto_trading.services;

import com.example.crypto_trading.domain.OrderType;
import com.example.crypto_trading.modal.Order;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.modal.Wallet;
import com.example.crypto_trading.respository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public void createWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(BigDecimal.ZERO);
        walletRepository.save(wallet);
    }

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepository.findByUserId(user.getId());
        if (wallet == null) {
            throw new RuntimeException("Wallet not found");
        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money) {
        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));
        wallet.setBalance(newBalance);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long id) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isEmpty()) {
            throw new RuntimeException("Wallet not found");
        }
        return wallet.get();
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet toWallet, Long money) {
        Wallet senderWallet = getUserWallet(sender);

        if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(money)) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        Wallet receiverWallet = getUserWallet(toWallet.getUser());
        addBalance(receiverWallet, money);
        addBalance(senderWallet, -money);

        return null;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) {
        Wallet userWallet = getUserWallet(user);
        if (order.getOrderType().equals(OrderType.BUY)) {
            BigDecimal newBalance = userWallet.getBalance().subtract(order.getPrice());
            if (newBalance.compareTo(order.getPrice()) < 0) {
                throw new RuntimeException("Insufficient balance");
            } else {
                userWallet.setBalance(newBalance);
            }
        } else if (order.getOrderType().equals(OrderType.SELL)) {
            addBalance(userWallet, order.getPrice().longValue());

        }

        walletRepository.save(userWallet);

        return userWallet;
    }
}
