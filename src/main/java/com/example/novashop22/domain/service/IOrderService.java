package com.example.novashop22.domain.service;

import com.example.novashop22.domain.model.OrderDTO;
import com.example.novashop22.domain.model.OrderResponse;

import java.util.List;

public interface IOrderService {

    OrderDTO placeOrder(String emailId, Long cartId, String paymentMethod);

    OrderDTO getOrder(String emailId, Long orderId);

    List<OrderDTO> getOrdersByUser(String emailId);

    OrderResponse getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    OrderDTO updateOrder(String emailId, Long orderId, String orderStatus);
}
