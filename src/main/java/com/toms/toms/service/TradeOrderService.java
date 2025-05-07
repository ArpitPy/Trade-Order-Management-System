package com.toms.toms.service;

import com.toms.toms.dto.CreateOrderRequest;
import com.toms.toms.dto.OrderResponse;
import com.toms.toms.dto.UpdateOrderRequest;

public interface TradeOrderService {
    OrderResponse placeOrder(CreateOrderRequest request);

    OrderResponse getOrderById(Long id);

    OrderResponse updateOrder(Long id, UpdateOrderRequest request);

    OrderResponse cancelOrder(Long id);
}
