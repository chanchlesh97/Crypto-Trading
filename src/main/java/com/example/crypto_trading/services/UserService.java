package com.example.crypto_trading.services;

import com.example.crypto_trading.domain.VerificationType;
import com.example.crypto_trading.modal.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
     User findUserProfileByJwt(String jwt) throws Exception;
//     User findUserProfileById(String id);
     User findUserProfileByEmail(String email) throws Exception;

     User enableTwoFactorAuth(VerificationType verificationType, String sendTo, User user);

//     User updatePassword(User user, String password);

}
