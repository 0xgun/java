package com.ecommerce.sportscenter.service;

import com.ecommerce.sportscenter.model.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse getProductById(Integer id);
    Page<ProductResponse> getAllProducts(Pageable pageable);
    List<ProductResponse> searchProductsByName(String string);
    List<ProductResponse> searchProductsByByBrandTypeAndName(Integer brandId, Integer typeId, String string);
    List<ProductResponse> searchProductsByByBrandType(Integer brandId, Integer typeId);
    List<ProductResponse> searchProductsByBrand(Integer brandId);
    List<ProductResponse> searchProductsByType(Integer typeId);
}
