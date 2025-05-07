package com.toms.toms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toms.toms.dto.CreateOrderRequest;
import com.toms.toms.dto.UpdateOrderRequest;
import com.toms.toms.model.OrderType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TradeOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldPlaceNewOrder() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .symbol("AAPL")
                .type(OrderType.BUY)
                .quantity(10)
                .price(BigDecimal.valueOf(150.25))
                .build();

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.symbol").value("AAPL"))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.price").value(150.25));
    }

    @Test
    void shouldReturnOrderById() throws Exception {
        // placing new order
        CreateOrderRequest request = CreateOrderRequest.builder()
                .symbol("MSFT")
                .type(OrderType.BUY)
                .quantity(15)
                .price(BigDecimal.valueOf(310.00))
                .build();

        String responseBody = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // id of created order
        Long orderId = objectMapper.readTree(responseBody).get("id").asLong();

        // api call
        mockMvc.perform(get("/api/orders/" + orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.symbol").value("MSFT"))
                .andExpect(jsonPath("$.type").value("BUY"))
                .andExpect(jsonPath("$.quantity").value(15))
                .andExpect(jsonPath("$.price").value(310.00));
    }

    @Test
    void shouldCancelOrder() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .symbol("TSLA")
                .type(OrderType.SELL)
                .quantity(20)
                .price(BigDecimal.valueOf(720.50))
                .build();

        String responseBody = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long orderId = objectMapper.readTree(responseBody).get("id").asLong();

        // cancelling order
        mockMvc.perform(delete("/api/orders/" + orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    @Test
    void shouldUpdateOrder() throws Exception {
        // Step 1: Create a new order
        CreateOrderRequest createRequest = CreateOrderRequest.builder()
                .symbol("GOOGL")
                .type(OrderType.BUY)
                .quantity(5)
                .price(BigDecimal.valueOf(2800.00))
                .build();

        String createResponse = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long orderId = objectMapper.readTree(createResponse).get("id").asLong();

        // Step 2: Update quantity and price
        UpdateOrderRequest updateRequest = UpdateOrderRequest.builder()
                .quantity(10)
                .price(Double.valueOf(2750.00))
                .build();

        mockMvc.perform(put("/api/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.price").value(2750.00));
    }

}
