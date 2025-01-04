package com.example.crypto_trading.services;

import com.example.crypto_trading.modal.Asset;
import com.example.crypto_trading.modal.Coin;
import com.example.crypto_trading.modal.User;

import java.util.List;

public interface AssetService {
    Asset createAsset(User user, Coin coin, double quantity);
    Asset getAssetById(Long id);
    Asset getAssetByUserIdAndId(Long userId, Long id);
    List<Asset> getAssetsByUserId(Long userId);

    Asset updateAsset(Long assetId, double quantity);

    Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

    void deleteAsset(Long assetId);
}
