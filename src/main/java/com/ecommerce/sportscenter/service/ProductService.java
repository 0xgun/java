package com.ecommerce.sportscenter.service;

import com.ecommerce.sportscenter.model.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse getProductById(Integer id);
    List<ProductResponse> getAllProducts();
}
