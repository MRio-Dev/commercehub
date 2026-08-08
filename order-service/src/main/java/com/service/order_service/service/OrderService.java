package com.service.order_service.service;

import com.service.order_service.dto.OrderLineItemDto;
import com.service.order_service.dto.OrderRequest;
import com.service.order_service.dto.OrderResponse;
import com.service.order_service.entity.Order;
import com.service.order_service.entity.OrderLineItem;
import com.service.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;


    public void placeOrder(OrderRequest orderRequest){
            Order order = Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .orderLineItems(orderRequest.getOrderLineItemDtoList()
                            .stream()
                            .map(this::mapToDto)
                            .toList())
                    .build();
            orderRepository.save(order);
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
