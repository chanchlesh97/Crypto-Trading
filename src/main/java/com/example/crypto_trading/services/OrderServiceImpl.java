package com.example.crypto_trading.services;

import com.example.crypto_trading.domain.OrderStatus;
import com.example.crypto_trading.domain.OrderType;
import com.example.crypto_trading.modal.Coin;
import com.example.crypto_trading.modal.Order;
import com.example.crypto_trading.modal.OrderItem;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.respository.OrderItemRepository;
import com.example.crypto_trading.respository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements  OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();
        Order order = new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setPrice(BigDecimal.valueOf(price));
        order.setOrderType(orderType);
        order.setTimeStamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {

        return orderRepository.findById(orderId).
                orElseThrow(()->new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) {
        return null;
    }
    private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice){
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepository.save(orderItem);
    }

    public Order buyAsset(User user, Coin coin, double quantity){
        if (quantity <= 0){
            throw new RuntimeException("Quantity must be greater than 0");
        }
        OrderItem orderItem = createOrderItem(coin, quantity, coin.getCurrentPrice(), 0);
        return createOrder(user, orderItem, OrderType.BUY);
    }
}
