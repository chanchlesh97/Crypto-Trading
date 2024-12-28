package com.example.crypto_trading.respository;

import com.example.crypto_trading.modal.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, String> {
}
