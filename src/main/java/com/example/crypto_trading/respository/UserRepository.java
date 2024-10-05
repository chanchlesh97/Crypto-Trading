package com.example.crypto_trading.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crypto_trading.modal.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
