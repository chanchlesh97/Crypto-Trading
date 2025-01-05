package com.example.crypto_trading.controller;

import com.example.crypto_trading.modal.Coin;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.modal.WatchList;
import com.example.crypto_trading.respository.WatchListRepository;
import com.example.crypto_trading.services.CoinService;
import com.example.crypto_trading.services.UserService;
import com.example.crypto_trading.services.WatchListService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {

    @Autowired
    private UserService userService;

    @Autowired
    private WatchListService watchListService;

    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        WatchList watchList = watchListService.findUserWatchList(user.getId());
        return ResponseEntity.ok(watchList);
    }

//    @PostMapping
//    public ResponseEntity<WatchList> createWatchList(@RequestHeader("Authorization") String jwt) throws Exception {
//        User user = userService.findUserProfileByJwt(jwt);
//        WatchList watchList = watchListService.createWatchList(user);
//        return ResponseEntity.ok(watchList);
//    }

    @GetMapping("/{watchListId}")
    public ResponseEntity<WatchList> getWatchList(@PathVariable Long watchListId) {
        WatchList watchList = watchListService.findById(watchListId);
        return ResponseEntity.ok(watchList);
    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addCoinToWatchList(@RequestHeader("Authorization") String jwt, @PathVariable String coinId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        WatchList watchList = watchListService.findUserWatchList(user.getId());
        Coin coin = coinService.findById(coinId);
        Coin addedCoin = watchListService.addCoinToWatchList(coin, user);
        return ResponseEntity.ok(addedCoin);
    }
}
