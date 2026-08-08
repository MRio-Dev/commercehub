package com.service.product_service.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String price;
    private String description;
}
