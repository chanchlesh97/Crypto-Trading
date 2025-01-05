package com.example.crypto_trading.services;

import com.example.crypto_trading.domain.PaymentMethod;
import com.example.crypto_trading.domain.PaymentOrderStatus;
import com.example.crypto_trading.modal.PaymentOrder;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.response.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentOrder createPaymentOrder(User user, Long amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long orderId) throws Exception;

    Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;
    PaymentResponse createRazorpayPaymentLink(User user, Long amount);
    PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;
}
