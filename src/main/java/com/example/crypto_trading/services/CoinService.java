package com.example.crypto_trading.services;

import com.example.crypto_trading.modal.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(int page);

    String getMarketChart(String coinId, String days);

    String getCoinDetails(String coinId);

    Coin findById(String coinId);

    String searchCoin(String keyword);

    String getTop50CoinsByMarketCapRank();

    String getTradingCoins();
}
