package com.example.crypto_trading.services;

import com.example.crypto_trading.modal.TwoFactorOTP;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.respository.TwoFactorOTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactorOTPServiceImpl implements TwoFactorOTPService{

    @Autowired
    private TwoFactorOTPRepository twoFactorOTPRepository;

    @Override
    public TwoFactorOTP createTwoFactorOTP(User user, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
        twoFactorOTP.setId(id);
        twoFactorOTP.setUser(user);
        twoFactorOTP.setOtp(otp);
        twoFactorOTP.setJwt(jwt);
        return twoFactorOTPRepository.save(twoFactorOTP);
    }

    @Override
    public TwoFactorOTP getTwoFactorOTP(User user) {
        return twoFactorOTPRepository.findByUserId(user.getId());
    }

    @Override
    public TwoFactorOTP findByUser(Long id) {

        return twoFactorOTPRepository.findByUserId(id);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        Optional<TwoFactorOTP> opt = twoFactorOTPRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public boolean verifyTwoFactorOTP(TwoFactorOTP twoFactorOTP, String otp) {
        return twoFactorOTP.getOtp().equals(otp);
    }

    @Override
    public void deleteTwoFactorOTP(TwoFactorOTP twoFactorOTP) {
        twoFactorOTPRepository.delete(twoFactorOTP);
    }
}
