package com.service.inventory_service.service;

import com.service.inventory_service.dto.OrderDto;
import com.service.inventory_service.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, Integer quantity){
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(
                skuCode,
                quantity
        );
    }

    public boolean areAllinStock(List<OrderDto> orders){
        for(OrderDto order : orders){
            if(!isInStock(order.getSkuCode(), order.getQuantity())){
                return false;
            }
        }
        return true;
    }


}
