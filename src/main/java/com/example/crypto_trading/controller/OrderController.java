package com.example.crypto_trading.controller;

import com.example.crypto_trading.domain.OrderType;
import com.example.crypto_trading.modal.Coin;
import com.example.crypto_trading.modal.Order;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.request.CreateOrderRequest;
import com.example.crypto_trading.services.CoinService;
import com.example.crypto_trading.services.OrderService;
import com.example.crypto_trading.services.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

//    @Autowired
//    private WalletTransactionService walletTransactionService;
//

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateOrderRequest orderRequest
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(orderRequest.getCoinId());
        Order order = orderService.processOrder(coin, orderRequest.getQuantity(), orderRequest.getOrderType(), user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.getOrderById(orderId);
        if(order.getUser().getId().equals(user.getId())){
            throw new Exception("You are not authorized to view this order");
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> getAllOrderOfUser(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(value = "orderType", required = false) OrderType orderType,
            @RequestParam(value = "assetSymbol", required = false) String assetSymbol
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> order = orderService.getAllOrderOfUser(user.getId(), orderType, assetSymbol);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
