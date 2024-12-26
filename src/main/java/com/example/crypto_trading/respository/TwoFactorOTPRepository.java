package com.example.crypto_trading.respository;

import com.example.crypto_trading.modal.TwoFactorOTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOTPRepository extends JpaRepository<TwoFactorOTP, String> {

//    @Autowired
//    private TwoFactorOTPRepository twoFactorOTPRepository;
//
//    @Override
//    TwoFactorOTP findByUser(Long userId){
//        return null;
//    }
//
//    @Override
//    TwoFactorOTP findById(String id){
//        return null;
//    }
    TwoFactorOTP findByUserId(String userId);
}
