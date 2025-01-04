package com.example.crypto_trading.controller;

import com.example.crypto_trading.modal.Asset;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.services.AssetService;
import com.example.crypto_trading.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private UserService userService;

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long assetId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Asset asset = assetService.getAssetById(assetId);
        if(asset.getUser().getId().equals(user.getId())){
            throw new Exception("You are not authorized to view this asset");
        }
        return new ResponseEntity<>(asset, HttpStatus.OK);
    }

    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Asset> getAssetByUserIdAndCoinId(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String coinId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coinId);
        if(asset.getUser().getId().equals(user.getId())){
            throw new Exception("You are not authorized to view this asset");
        }
        return new ResponseEntity<>(asset, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Asset>> getAssetsByUserId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Asset> assets = assetService.getAssetsByUserId(user.getId());
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
}
