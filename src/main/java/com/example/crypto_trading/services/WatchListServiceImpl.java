package com.example.crypto_trading.services;

import com.example.crypto_trading.modal.*;
import com.example.crypto_trading.respository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchListServiceImpl implements WatchListService{

    @Autowired
    private WatchListRepository watchListRepository;

    @Override
    public WatchList findUserWatchList(Long userId) {
        WatchList watchList = watchListRepository.findByUserId(userId);
        if(watchList == null){
            throw new RuntimeException("Watchlist not found");
        }
        return watchList;
    }

    @Override
    public WatchList createWatchList(User user) {
        WatchList watchList = new WatchList();

        watchList.setUser(user);
        return watchListRepository.save(watchList);
    }

    @Override
    public WatchList findById(Long watchListId) {
        Optional<WatchList> watchList = watchListRepository.findById(watchListId);
        if(watchList.isEmpty()){
            throw new RuntimeException("Watchlist not found");
        }
        return watchList.get();
    }

    @Override
    public Coin addCoinToWatchList(Coin coin, User user) {
        WatchList watchList = findUserWatchList(user.getId());

        if(watchList.getCoins().contains(coin)){
            watchList.getCoins().remove(coin);
        }else{
            watchList.getCoins().add(coin);
        }

        watchListRepository.save(watchList);
        return coin;
    }
}
