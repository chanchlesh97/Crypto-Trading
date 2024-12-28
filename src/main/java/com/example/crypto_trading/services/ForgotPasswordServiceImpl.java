package com.example.crypto_trading.services;

import com.example.crypto_trading.domain.VerificationType;
import com.example.crypto_trading.modal.ForgotPasswordToken;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.respository.ForgotPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService{

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;


    @Override
    public ForgotPasswordToken createForgotPasswordToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {

        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setUser(user);
        forgotPasswordToken.setOtp(otp);
        forgotPasswordToken.setId(id);
        forgotPasswordToken.setSendTo(sendTo);
        forgotPasswordToken.setVerificationType(verificationType);

        return forgotPasswordRepository.save(forgotPasswordToken);
    }

    @Override
    public ForgotPasswordToken findForgotPasswordTokenByUser(User user) {

        ForgotPasswordToken forgotPasswordToken = forgotPasswordRepository.findByUserId(user.getId());
        Optional<ForgotPasswordToken> opt = Optional.ofNullable(forgotPasswordToken);
        return forgotPasswordToken;
    }

    @Override
    public ForgotPasswordToken findForgotPasswordTokenById(Long id){
        ForgotPasswordToken forgotPasswordToken = forgotPasswordRepository.findByUserId(id);
        return forgotPasswordToken;
    }

    @Override
    public void deleteForgotPasswordToken(ForgotPasswordToken forgotPasswordToken) {

    }
}

