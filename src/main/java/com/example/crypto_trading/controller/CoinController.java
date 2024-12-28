package com.example.crypto_trading.controller;

import com.example.crypto_trading.modal.Coin;
import com.example.crypto_trading.services.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coin")
public class CoinController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CoinService coinService;

    @GetMapping
    ResponseEntity<List<Coin>> getCoinList(@RequestParam("page") int page) throws Exception {
        List<Coin> coinList = coinService.getCoinList(page);
        return new ResponseEntity<>(coinList, HttpStatus.ACCEPTED ) ;
    }

    @GetMapping("/{coinId}/chart")
    ResponseEntity<JsonNode> getMarketChart(@PathVariable("coinId") String coinId, @RequestParam("days") String days) throws Exception {
        String marketChart = coinService.getMarketChart(coinId, days);
        JsonNode jsonNode = objectMapper.readTree(marketChart);
        return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED ) ;
    }

    @GetMapping("/search")
    ResponseEntity<JsonNode> searchCoin(@RequestParam("q") String keyword) throws JsonProcessingException {
        String searchCoin = coinService.searchCoin(keyword);
        JsonNode jsonNode = objectMapper.readTree(searchCoin);
        return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED ) ;
    }

    @GetMapping("/top50")
    ResponseEntity<JsonNode> getTop50CoinsByMarketCapRank() throws JsonProcessingException {
        String top50CoinsByMarketCapRank = coinService.getTop50CoinsByMarketCapRank();
        JsonNode jsonNode = objectMapper.readTree(top50CoinsByMarketCapRank);
        return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED ) ;
    }

    @GetMapping("/trading")
    ResponseEntity<JsonNode> getTradingCoins() throws JsonProcessingException {
        String tradingCoins = coinService.getTradingCoins();
        JsonNode jsonNode = objectMapper.readTree(tradingCoins);
        return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED ) ;
    }

    @GetMapping("/details/{coinId}")
    ResponseEntity<JsonNode> getCoinDetails(@PathVariable("coinId") String coinId) throws JsonProcessingException {
        String coinDetails = coinService.getCoinDetails(coinId);
        JsonNode jsonNode = objectMapper.readTree(coinDetails);
        return new ResponseEntity<>(jsonNode, HttpStatus.ACCEPTED ) ;
    }



}
