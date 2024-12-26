package com.example.crypto_trading.utils;

import java.util.Random;

public class OtpUtils {
    public static String generateOTP() {
        int otpLegth = 6;
        Random random = new Random();
        StringBuilder otp = new StringBuilder(otpLegth);

        for(int i = 0; i < otpLegth; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
