package com.example.crypto_trading.services;

import com.example.crypto_trading.domain.OrderType;
import com.example.crypto_trading.modal.Coin;
import com.example.crypto_trading.modal.Order;
import com.example.crypto_trading.modal.OrderItem;
import com.example.crypto_trading.modal.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType);
    Order getOrderById(Long orderId);
    List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

}
