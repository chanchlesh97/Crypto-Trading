package com.example.crypto_trading.controller;

import com.example.crypto_trading.config.JwdProvider;
import com.example.crypto_trading.response.AuthResponse;
import com.example.crypto_trading.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.respository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	CustomUserDetailsService customUserDetailsService;

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

		AuthResponse res = new AuthResponse();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("User logged in successfully...");

		return new ResponseEntity<>(res, HttpStatus.CREATED);

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
}
