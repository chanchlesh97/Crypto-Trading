package com.example.crypto_trading.services;

import com.example.crypto_trading.modal.Asset;
import com.example.crypto_trading.modal.Coin;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.respository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService{

    @Autowired
    private AssetRepository assetRepository;

    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        Asset asset = new Asset();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());
        return assetRepository.save(asset);
    }

    @Override
    public Asset getAssetById(Long id) {

        return assetRepository.findById(id).orElseThrow(()->new RuntimeException("Asset not found"));
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long id) {
        return null;

    }

    @Override
    public List<Asset> getAssetsByUserId(Long userId){
        return assetRepository.findByUserId(userId);
        //.orElseThrow(()->new RuntimeException("Asset not found"));
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) {

        Asset asset = getAssetById(assetId);
        asset.setQuantity(asset.getQuantity()+quantity);
        return assetRepository.save(asset);
//        return null;
    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
        return assetRepository.findByUserIdAndCoinId(userId, coinId);
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetRepository.deleteById(assetId);
    }
}
