package com.toms.toms.controller;

import com.toms.toms.dto.CreateOrderRequest;
import com.toms.toms.dto.OrderResponse;
import com.toms.toms.dto.UpdateOrderRequest;
import com.toms.toms.service.TradeOrderService;
import jakarta.persistence.PreUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class TradeOrderController {
    private final TradeOrderService tradeOrderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderResponse response = tradeOrderService.placeOrder(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        OrderResponse response = tradeOrderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @Valid @RequestBody UpdateOrderRequest request) {
        OrderResponse response = tradeOrderService.updateOrder(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id){
        OrderResponse response = tradeOrderService.cancelOrder(id);
        return ResponseEntity.ok(response);
    }
}
