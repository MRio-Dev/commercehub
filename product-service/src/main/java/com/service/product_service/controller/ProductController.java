package com.service.product_service.controller;

import com.service.product_service.dto.ProductRequest;
import com.service.product_service.dto.ProductResponse;
import com.service.product_service.dto.ProductUpdateRequest;
import com.service.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    ResponseEntity<List<ProductResponse>> getProducts(){
        List<ProductResponse> products =  productService.getAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @PutMapping
    ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductUpdateRequest productUpdateRequest){
        try{
            ProductResponse updatedProduct = productService.updateProduct(productUpdateRequest);
            return new ResponseEntity<>(updatedProduct, HttpStatus.ACCEPTED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable String id){
        try{
            return ResponseEntity.ok(productService.deleteProduct(id));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest request) {
        productService.addProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Product added successfully");
    }

}
