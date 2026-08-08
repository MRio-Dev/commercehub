package com.service.product_service.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("product")
@Data
@Builder
public class Product {
    @Id
    private String id;
    private String name;
    private String price;
    private String description;
}
