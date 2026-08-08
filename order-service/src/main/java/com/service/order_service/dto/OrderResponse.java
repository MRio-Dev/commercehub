package com.service.order_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResponse {
    private String orderNumber;
    private List<OrderLineItemDto> orderLineItemDtoList;
}
