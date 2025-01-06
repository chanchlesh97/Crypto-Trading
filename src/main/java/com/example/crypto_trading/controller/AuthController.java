package com.example.crypto_trading.controller;

import com.example.crypto_trading.config.JwdProvider;
import com.example.crypto_trading.modal.TwoFactorOTP;
import com.example.crypto_trading.response.AuthResponse;
import com.example.crypto_trading.services.CustomUserDetailsService;
import com.example.crypto_trading.services.EmailService;
import com.example.crypto_trading.services.TwoFactorOTPService;
import com.example.crypto_trading.services.WatchListService;
import com.example.crypto_trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.respository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private TwoFactorOTPService twoFactorOTPService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private WatchListService watchListService;

	@GetMapping("/signup")
    public String signUpPage() {
        return "auth/signup";
    }

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {


		User isExist = userRepository.findByEmail(user.getEmail());

		if(isExist != null) {
			throw  new Exception("User already exist...");
		}
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		newUser.setFullName(user.getFullName());
		User savedUser = userRepository.save(newUser);

		watchListService.createWatchList(savedUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				user.getEmail(),
				user.getPassword()
				);

		SecurityContextHolder.getContext().setAuthentication(authentication);


		String jwt = JwdProvider.generateToken(authentication);

		AuthResponse res = new AuthResponse();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("User registered successfully...");

		return new ResponseEntity<>(res, HttpStatus.CREATED);

	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

		String userName = user.getEmail();
		String password = user.getPassword();

		Authentication authentication = authenticate(userName, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);


		String jwt = JwdProvider.generateToken(authentication);

		User authUser = userRepository.findByEmail(userName);

		if(authUser.getTwoFactorAuth().isEnabled()){
			AuthResponse res = new AuthResponse();
			res.setMessage("Two factor authentication enabled");
			res.setTwoFactorAuthEnabled(true);
			String otp = OtpUtils.generateOTP();

			TwoFactorOTP oldTwoFactorOTP = twoFactorOTPService.findByUser(authUser.getId());

			if(oldTwoFactorOTP != null){
				twoFactorOTPService.deleteTwoFactorOTP(oldTwoFactorOTP);
			}
			TwoFactorOTP newTwoFactorOTP = twoFactorOTPService.createTwoFactorOTP(authUser, otp, jwt);
			emailService.sendVerificationOtpEmail(authUser.getEmail(), otp);
			res.setSession(newTwoFactorOTP.getId());

			return new ResponseEntity<>(res, HttpStatus.CREATED);
		}
		AuthResponse res = new AuthResponse();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("User logged in successfully...");

		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

	}

	private Authentication authenticate(String userName, String password) {

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

		if(userDetails == null){
			throw new BadCredentialsException("User not found");
		}
		if(!password.equals(userDetails.getPassword())){
			throw new BadCredentialsException("Invalid password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	}

	@PostMapping("/two-factor/otp/{otp}")
	public ResponseEntity<AuthResponse> verifySigninOtp(
			@PathVariable String otp,
			@RequestParam String id
	)throws Exception{
		TwoFactorOTP twoFactorOTP = twoFactorOTPService.findById(id);

		if(twoFactorOTPService.verifyTwoFactorOTP(twoFactorOTP, otp)){
			AuthResponse res = new AuthResponse();
			res.setStatus(true);
			res.setMessage("Two Factor Authentication success");
			res.setTwoFactorAuthEnabled(true);
			res.setJwt(twoFactorOTP.getJwt());
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
		throw new Exception("Invalid OTP");
	}
}
