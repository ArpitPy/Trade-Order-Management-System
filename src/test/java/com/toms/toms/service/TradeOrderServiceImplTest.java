package com.toms.toms.service;

import com.toms.toms.dto.CreateOrderRequest;
import com.toms.toms.dto.OrderResponse;
import com.toms.toms.model.OrderStatus;
import com.toms.toms.model.OrderType;
import com.toms.toms.model.TradeOrder;
import com.toms.toms.repository.TradeOrderRepository;
import com.toms.toms.service.impl.TradeOrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TradeOrderServiceImplTest {

    @Mock
    private TradeOrderRepository tradeOrderRepository;

    @InjectMocks
    private TradeOrderServiceImpl tradeOrderService;

    @Test
    void testPlaceOrder_shouldReturnOrderResponse() {
        CreateOrderRequest request = new CreateOrderRequest("AAPL", OrderType.BUY, 100, new BigDecimal("150.00"));

        TradeOrder savedOrder = TradeOrder.builder()
                .id(1L)
                .symbol("AAPL")
                .type(OrderType.BUY)
                .quantity(100)
                .price(new BigDecimal("150.00"))
                .status(OrderStatus.NEW)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(tradeOrderRepository.save(any(TradeOrder.class))).thenReturn(savedOrder);

        OrderResponse response = tradeOrderService.placeOrder(request);

        assertNotNull(response);
        assertEquals("AAPL", response.getSymbol());
        assertEquals(OrderStatus.NEW, response.getStatus());

        verify(tradeOrderRepository, times(1)).save(any(TradeOrder.class));
    }

    @Test
    void testGetOrderById_shouldReturnOrderResponse() {
        TradeOrder order = TradeOrder.builder()
                .id(1L)
                .symbol("GOOG")
                .type(OrderType.SELL)
                .quantity(50)
                .price(new BigDecimal("2000.00"))
                .status(OrderStatus.NEW)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(tradeOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderResponse response = tradeOrderService.getOrderById(1L);

        assertNotNull(response);
        assertEquals("GOOG", response.getSymbol());
    }

    @Test
    void testCancelOrder_shouldUpdateStatus() {
        TradeOrder order = TradeOrder.builder()
                .id(1L)
                .symbol("TSLA")
                .type(OrderType.SELL)
                .quantity(10)
                .price(new BigDecimal("700.00"))
                .status(OrderStatus.NEW)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(tradeOrderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(tradeOrderRepository.save(any(TradeOrder.class))).thenReturn(order);

        OrderResponse response = tradeOrderService.cancelOrder(1L);

        assertEquals(OrderStatus.CANCELLED, response.getStatus());
    }
}
