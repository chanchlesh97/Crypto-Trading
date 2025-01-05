package com.example.crypto_trading.services;

import com.example.crypto_trading.modal.PaymentDetail;
import com.example.crypto_trading.modal.User;
import com.example.crypto_trading.respository.PaymentDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailServiceImpl implements PaymentDetailService{
    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Override
    public PaymentDetail addPaymentDetails(String accountNumber, String accoundHolderName, String ifsc, String bankName, User user) {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setAccountNumber(accountNumber);
        paymentDetail.setAccoundHolderName(accoundHolderName);
        paymentDetail.setIfsc(ifsc);
        paymentDetail.setBankName(bankName);
        paymentDetail.setUser(user);
        return paymentDetailRepository.save(paymentDetail);

    }

    @Override
    public PaymentDetail getUserPaymentDetails(User user) {
        return paymentDetailRepository.findByUserId(user.getId());
    }
}
