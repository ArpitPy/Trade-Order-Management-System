package com.toms.toms.service.impl;

import com.toms.toms.dto.CreateOrderRequest;
import com.toms.toms.dto.OrderResponse;
import com.toms.toms.model.TradeOrder;
import com.toms.toms.repository.TradeOrderRepository;
import com.toms.toms.service.TradeOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeOrderServiceImpl implements TradeOrderService {
    private final TradeOrderRepository tradeOrderRepository;

    @Override
    public OrderResponse placeOrder(CreateOrderRequest request) {
        //mapping dto to entity
        TradeOrder order = TradeOrder.builder()
                .symbol(request.getSymbol())
                .type(request.getType())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        //saving to DB
        TradeOrder saved = tradeOrderRepository.save(order);

        //mapping entity to response dto
        return OrderResponse.builder()
                .id(saved.getIdentity())
                .symbol(saved.getSymbol())
                .type(saved.getType())
                .quantity(saved.getQuantity())
                .price(saved.getPrice())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }
}
