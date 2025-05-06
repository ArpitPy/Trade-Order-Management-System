package com.toms.toms.service;

import com.toms.toms.dto.CreateOrderRequest;
import com.toms.toms.dto.OrderResponse;

public interface TradeOrderService {
    OrderResponse placeOrder(CreateOrderRequest request);
}
