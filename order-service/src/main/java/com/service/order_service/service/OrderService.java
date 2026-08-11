package com.service.order_service.service;

import com.service.order_service.dto.*;
import com.service.order_service.entity.Order;
import com.service.order_service.entity.OrderLineItem;
import com.service.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
            Order order = Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .orderLineItems(orderRequest.getOrderLineItemDtoList()
                            .stream()
                            .map(this::mapToDto)
                            .toList())
                    .build();

            List<OrderDto> orders = orderRequest.getOrderLineItemDtoList()
                    .stream().map(orderLineItemDto -> (
                            OrderDto.builder()
                                    .quantity(orderLineItemDto.getQuantity())
                                    .skuCode(orderLineItemDto.getSkuCode())
                                    .build()
                    )).toList();
            InventoryCheckDto inventoryCheckDto = InventoryCheckDto.builder()
                    .orders(orders)
                    .build();

            log.info("Checking Inventory: {}", inventoryCheckDto.toString());
            boolean check = Boolean.TRUE.equals(webClient
                    .method(HttpMethod.GET)
                    .uri("http://localhost:8083/api/inventory/all")
                    .bodyValue(inventoryCheckDto)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block());


            if(check){
                log.info("Items are available in inventory for products");
                orderRepository.save(order);
            }else{
                throw new IllegalArgumentException("Items not in Inventory :(");
            }
    }

    public List<OrderResponse> getALlOrders(){
        return orderRepository.findAll().stream()
                .map(this::mapOrderToResponse)
                .toList();
    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto){
        return OrderLineItem.builder()
                .skuCode(orderLineItemDto.getSkuCode())
                .quantity(orderLineItemDto.getQuantity())
                .price(orderLineItemDto.getPrice())
                .build();
    }

    private OrderResponse mapOrderToResponse(Order order){
        return OrderResponse.builder()
                .orderNumber(order.getOrderNumber())
                .orderLineItemDtoList(order.getOrderLineItems().stream()
                        .map(this::mapFromDto)
                        .toList())
                .build();
    }


    private OrderLineItemDto mapFromDto(OrderLineItem orderLineItem){
        return OrderLineItemDto.builder()
                .price(orderLineItem.getPrice())
                .skuCode(orderLineItem.getSkuCode())
                .quantity(orderLineItem.getQuantity())
                .build();
    }

}
