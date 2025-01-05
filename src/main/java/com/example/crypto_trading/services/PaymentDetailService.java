package com.example.crypto_trading.services;

import com.example.crypto_trading.modal.PaymentDetail;
import com.example.crypto_trading.modal.User;

public interface PaymentDetailService {
    public PaymentDetail addPaymentDetails( String accountNumber,
                                            String accoundHolderName,
                                            String ifsc,
                                            String bankName,
                                            User user);
    public PaymentDetail getUserPaymentDetails(User user);
}
