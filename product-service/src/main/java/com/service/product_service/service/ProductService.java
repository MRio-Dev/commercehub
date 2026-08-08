package com.service.product_service.service;

import com.service.product_service.dto.ProductRequest;
import com.service.product_service.dto.ProductResponse;
import com.service.product_service.dto.ProductUpdateRequest;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProduct();

    ProductResponse getProductById(String id);

    void addProduct(ProductRequest productRequest);

    ProductResponse deleteProduct(String id);

    ProductResponse updateProduct(ProductUpdateRequest product);

}
