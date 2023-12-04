package com.javatechie.orderservice.Services;

import com.javatechie.orderservice.Entity.Order;
import com.javatechie.orderservice.Repository.OrderRepository;
import com.javatechie.orderservice.common.Payment;
import com.javatechie.orderservice.common.TransactionRequest;
import com.javatechie.orderservice.common.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private final RestTemplate restTemplate;
    private final OrderRepository repository;

    @Autowired
    public OrderService(RestTemplate restTemplate, OrderRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public TransactionResponse saveOrder(TransactionRequest request) {
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        Payment paymentResponse = restTemplate.postForObject("http://PAYMENT-SERVICE/payment/doPayment", payment, Payment.class);

        String response = (paymentResponse != null && "success".equals(paymentResponse.getPaymentStatus()))
                ? "Payment processing successful and order placed"
                : "There is a failure in the payment API, order added to cart";

        repository.save(order);
        return new TransactionResponse(order, paymentResponse.getAmount(),
                paymentResponse.getTransactionId(), response);
    }
}
