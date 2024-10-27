package com.example.cafe.service;

import com.example.cafe.model.Order;
import com.example.cafe.model.Payment;
import com.example.cafe.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment editPayment(Long id, Payment new_payment) {
        Optional<Payment> old = paymentRepository.findById(id);
        Payment old_payment = old.get();
        old_payment.setPaymentMethod(new_payment.getPaymentMethod());
        old_payment.setOrder(new_payment.getOrder());
        old_payment.setPaidAt(new_payment.getPaidAt());
        old_payment.setTotalAmount(new_payment.getTotalAmount());
        return paymentRepository.save(old_payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
