package com.example.crypto_trading.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private boolean status;
    private String message;
    private boolean twoFactorAuthEnabled;
    private String session;
}
