package com.example.crypto_trading.controller;

import com.example.crypto_trading.domain.VerificationType;
import com.example.crypto_trading.modal.ForgotPasswordToken;
import com.example.crypto_trading.request.ForgotPasswordTokenRequest;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.modal.VerificationCode;
import com.example.crypto_trading.request.ResetPasswordRequest;
import com.example.crypto_trading.response.ApiResponse;
import com.example.crypto_trading.response.AuthResponse;
import com.example.crypto_trading.services.EmailService;
import com.example.crypto_trading.services.ForgotPasswordService;
import com.example.crypto_trading.services.UserService;
import com.example.crypto_trading.services.VerificationCodeService;
import com.example.crypto_trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestParam("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestParam("Authorization") String jwt,
            @PathVariable VerificationType verificationType
            ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeById(user.getId());
        if(verificationCode == null){
            verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        }

        if(verificationType == VerificationType.EMAIL) {
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }
        return new ResponseEntity<>("Verification OTP sent successfully!", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
            @RequestParam("Authorization") String jwt,
            @PathVariable String otp
            ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeById(user.getId());
        String sendTo =  verificationCode.getVerificationType().equals(VerificationType.EMAIL) ? user.getEmail() : user.getMobile();
        boolean isVerified = verificationCode.getOtp().equals(otp);
        if(isVerified){
            User updatedUser = userService.enableTwoFactorAuth(verificationCode.getVerificationType(), sendTo, user);
            verificationCodeService.deleteVerificationCode(verificationCode);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        throw new Exception("Invalid OTP");
    }

    @PostMapping("/api/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
            @RequestBody ForgotPasswordTokenRequest forgotPasswordTokenRequest
            ) throws Exception {
        User user = userService.findUserProfileByEmail(forgotPasswordTokenRequest.getSendTo());

        String otp = OtpUtils.generateOTP();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken token = forgotPasswordService.findForgotPasswordTokenByUser(user);
        if(token == null){
            forgotPasswordService.createForgotPasswordToken(user, id, otp, forgotPasswordTokenRequest.getVerificationType(), forgotPasswordTokenRequest.getSendTo());
        }

        if(forgotPasswordTokenRequest.getVerificationType() == VerificationType.EMAIL) {
            emailService.sendVerificationOtpEmail(forgotPasswordTokenRequest.getSendTo(), otp);
        }

        AuthResponse response = new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Forgot password OTP send successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/api/users/reset-password/verify-otp/{otp}")
    public ResponseEntity<ApiResponse> verifyResetPasswordOtp(
            @RequestParam Long id,
            @RequestBody ResetPasswordRequest resetPasswordRequest
            ) throws Exception {

        ForgotPasswordToken token = forgotPasswordService.findForgotPasswordTokenById(id);

        boolean isVerified = token.getOtp().equals(resetPasswordRequest.getOtp());
        if(isVerified){
            ApiResponse response = new ApiResponse();
            response.setMessage("OTP verified successfully");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        throw new Exception("Invalid OTP");
    }
}
