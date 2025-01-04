package com.example.crypto_trading.services;

import com.example.crypto_trading.domain.OrderStatus;
import com.example.crypto_trading.domain.OrderType;
import com.example.crypto_trading.modal.*;
import com.example.crypto_trading.respository.OrderItemRepository;
import com.example.crypto_trading.respository.OrderRepository;
import jakarta.transaction.Transactional;
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

    @Autowired
    private AssetService assetService;

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
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
        if(orderType == OrderType.BUY){
            return buyAsset(user, coin, quantity);
        }else if(orderType == OrderType.SELL){
            return sellAsset(user, coin, quantity);
        }
        throw  new Exception("Invalid Order Type");
    }
    private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice){
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public Order buyAsset(User user, Coin coin, double quantity){
        if (quantity <= 0){
            throw new RuntimeException("Quantity must be greater than 0");
        }
        OrderItem orderItem = createOrderItem(coin, quantity, coin.getCurrentPrice(), 0);
        Order order = createOrder(user, orderItem, OrderType.BUY);
        orderItem.setOrder(order);

        walletService.payOrderPayment(order, user);
        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order savedOrder = orderRepository.save(order);

        // create asset
        Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());
        if(asset == null){
            asset = assetService.createAsset(user, coin, quantity);

        }else{
            assetService.updateAsset(asset.getId(), quantity);
        }

        return savedOrder;


    }

    @Transactional
    public Order sellAsset(User user, Coin coin, double quantity){
        if (quantity <= 0){
            throw new RuntimeException("Quantity must be greater than 0");
        }
        double sellPrice = coin.getCurrentPrice();
        Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());
        if(assetToSell == null){
            throw new RuntimeException("Asset not found");
        }
        double buyPrice = assetToSell.getBuyPrice();
        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);
        Order order = createOrder(user, orderItem, OrderType.SELL);
        orderItem.setOrder(order);

        if(assetToSell.getQuantity() >= quantity){
//          throw new RuntimeException("Insufficient quantity to sell");
            walletService.payOrderPayment(order, user);
            order.setStatus(OrderStatus.SUCCESS);
            order.setOrderType(OrderType.SELL);
            Order savedOrder = orderRepository.save(order);

            Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);
            if(updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1){
                assetService.deleteAsset(updatedAsset.getId());
            }
            return savedOrder;
        }

        throw new RuntimeException("Insufficient quantity to sell");


    }
}
