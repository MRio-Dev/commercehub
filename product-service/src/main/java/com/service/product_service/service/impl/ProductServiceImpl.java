package com.service.product_service.service.impl;

import com.service.product_service.dto.ProductRequest;
import com.service.product_service.dto.ProductResponse;
import com.service.product_service.dto.ProductUpdateRequest;
import com.service.product_service.model.Product;
import com.service.product_service.repository.ProductRepository;
import com.service.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAllProduct() {

        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Product product : products){
            productResponses.add(
                    ProductResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .build()
            );

        }
        return productResponses;
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not Found, id: " + id));

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();

    }

    @Override
    public void addProduct(ProductRequest productRequest) {

       productRepository.save(
                Product.builder()
                        .name(productRequest.getName())
                        .description(productRequest.getDescription())
                        .price(productRequest.getPrice())
                        .build()
        );


    }

    @Override
    public ProductResponse deleteProduct(String id) {

        Product product = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found, id: " + id)
        );

        productRepository.deleteById(id);

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    @Override
    public ProductResponse updateProduct(ProductUpdateRequest updateRequest) {

        Product product = productRepository.findById(updateRequest.getId()).orElseThrow(
                () -> new RuntimeException("Product not found, id: " + updateRequest.getId())
        );

        productRepository.save(
                Product.builder()
                        .id(updateRequest.getId())
                        .description(updateRequest.getDescription())
                        .price(updateRequest.getPrice())
                        .name(updateRequest.getName())
                        .build()
        );

        return getProductById(updateRequest.getId());
    }
}
