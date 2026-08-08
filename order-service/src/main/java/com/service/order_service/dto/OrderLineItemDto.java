package com.service.order_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemDto {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
