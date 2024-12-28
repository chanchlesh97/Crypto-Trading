package com.example.crypto_trading.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "coins")
public class Coin {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("name")
    private String name;

    @JsonProperty("image")
    private String image;

    @Column(name = "current_price")
    @JsonProperty("current_price")
    private double currentPrice;

    @Column(name = "market_cap")
    @JsonProperty("market_cap")
    private long marketCap;

    @Column(name = "market_cap_rank")
    @JsonProperty("market_cap_rank")
    private int marketCapRank;

    @Column(name = "fully_diluted_valuation")
    @JsonProperty("fully_diluted_valuation")
    private Long fullyDilutedValuation;

    @Column(name = "total_volume")
    @JsonProperty("total_volume")
    private long totalVolume;

    @Column(name = "high_24h")
    @JsonProperty("high_24h")
    private double high24h;

    @Column(name = "low_24h")
    @JsonProperty("low_24h")
    private double low24h;

    @Column(name = "price_change_24h")
    @JsonProperty("price_change_24h")
    private double priceChange24h;

    @Column(name = "price_change_percentage_24h")
    @JsonProperty("price_change_percentage_24h")
    private double priceChangePercentage24h;

    @Column(name = "market_cap_change_24h")
    @JsonProperty("market_cap_change_24h")
    private double marketCapChange24h;

    @Column(name = "market_cap_change_percentage_24h")
    @JsonProperty("market_cap_change_percentage_24h")
    private double marketCapChangePercentage24h;

    @Column(name = "circulating_supply")
    @JsonProperty("circulating_supply")
    private double circulatingSupply;

    @Column(name = "total_supply")
    @JsonProperty("total_supply")
    private double totalSupply;

    @Column(name = "max_supply")
    @JsonProperty("max_supply")
    private Double maxSupply;

    @JsonProperty("ath")
    private double ath;

    @Column(name = "ath_change_percentage")
    @JsonProperty("ath_change_percentage")
    private double athChangePercentage;

    @Column(name = "ath_date")
    @JsonProperty("ath_date")
    private Date athDate;

    @JsonProperty("atl")
    private double atl;

    @Column(name = "atl_change_percentage")
    @JsonProperty("atl_change_percentage")
    private double atlChangePercentage;

    @Column(name = "atl_date")
    @JsonProperty("atl_date")
    private Date atlDate;

    @JsonProperty("roi")
    @JsonIgnore
    private String roi;

    @Column(name = "last_updated")
    @JsonProperty("last_updated")
    private LocalDateTime lastUpdated;

}
