package com.toms.toms.service.impl;

import com.toms.toms.dto.CreateOrderRequest;
import com.toms.toms.dto.OrderResponse;
import com.toms.toms.dto.UpdateOrderRequest;
import com.toms.toms.model.OrderStatus;
import com.toms.toms.model.TradeOrder;
import com.toms.toms.repository.TradeOrderRepository;
import com.toms.toms.service.TradeOrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
                .id(saved.getId())
                .symbol(saved.getSymbol())
                .type(saved.getType())
                .quantity(saved.getQuantity())
                .price(saved.getPrice())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        TradeOrder order = tradeOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        return OrderResponse.builder()
                .id(order.getId())
                .symbol(order.getSymbol())
                .type(order.getType())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    @Override
    public OrderResponse updateOrder(Long id, UpdateOrderRequest request) {
        TradeOrder order = tradeOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        if (order.getStatus() != OrderStatus.NEW) {
            throw new IllegalStateException("Only NEW orders can be modified!");
        }

        order.setQuantity(request.getQuantity());
        order.setPrice(BigDecimal.valueOf(request.getPrice()));
        order.setUpdatedAt(LocalDateTime.now());

        TradeOrder updated = tradeOrderRepository.save(order);

        return OrderResponse.builder()
                .id(updated.getId())
                .symbol(updated.getSymbol())
                .type(updated.getType())
                .quantity(updated.getQuantity())
                .price(updated.getPrice())
                .status(updated.getStatus())
                .createdAt(updated.getCreatedAt())
                .updatedAt(updated.getUpdatedAt())
                .build();
    }

    @Override
    public OrderResponse cancelOrder(Long id) {
        TradeOrder order = tradeOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        if (order.getStatus() != OrderStatus.NEW && order.getStatus() != OrderStatus.PARTIALLY_FILLED) {
            throw new IllegalStateException("Only NEW and PARTIALLY_FILLED orders can be cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());

        TradeOrder cancelled = tradeOrderRepository.save(order);

        return OrderResponse.builder()
                .id(cancelled.getId())
                .symbol(cancelled.getSymbol())
                .type(cancelled.getType())
                .quantity(cancelled.getQuantity())
                .price(cancelled.getPrice())
                .status(cancelled.getStatus())
                .createdAt(cancelled.getCreatedAt())
                .updatedAt(cancelled.getUpdatedAt())
                .build();
    }
}
