package com.example.crypto_trading.services;

import com.example.crypto_trading.modal.Coin;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.modal.WatchList;

public interface WatchListService {
    WatchList findUserWatchList(Long userId);
    WatchList createWatchList(User user);
    WatchList findById(Long watchListId);
    Coin addCoinToWatchList(Coin coin, User user);
}
