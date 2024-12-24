package com.example.crypto_trading.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping
	public String home() {
		return "Welcome to Home";
	}

	@GetMapping("/home")
	public String secure() {
		return "Welcome to Secure Home";
	}

}
