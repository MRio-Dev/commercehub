package com.service.inventory_service.controller;


import com.service.inventory_service.dto.InventoryCheckDto;
import com.service.inventory_service.service.InventoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity){
        return inventoryService.isInStock(skuCode, quantity);
    }

    @GetMapping("/all")
    public boolean areAllinStock(@RequestBody InventoryCheckDto inventoryCheckDto){

        return inventoryService.areAllinStock(inventoryCheckDto.getOrders());
    }
}
