package com.example.crypto_trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@GetMapping("/signup")
    public String signUpPage() {
        return "auth/signup";
    }

	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody User user){
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		newUser.setFullName(user.getFullName());

		User savedUser = userRepository.save(newUser);

		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

	}
}
