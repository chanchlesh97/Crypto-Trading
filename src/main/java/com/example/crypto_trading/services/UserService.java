package com.example.crypto_trading.services;

import com.example.crypto_trading.domain.VerificationType;
import com.example.crypto_trading.modal.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserProfileById(String id);
    public User findUserProfileByEmail(String email) throws Exception;

    public User enableTwoFactorAuth(VerificationType verificationType, String sendTo, User user);

    public User updatePassword(User user, String password);

}
