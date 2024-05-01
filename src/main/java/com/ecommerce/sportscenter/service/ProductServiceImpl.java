package com.ecommerce.sportscenter.service;

import com.ecommerce.sportscenter.entity.Product;
import com.ecommerce.sportscenter.model.ProductResponse;
import com.ecommerce.sportscenter.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse getProductById(Integer id) {
        log.info("Fetching product by ID  {}",id);
        Product product =productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product with give id doest not exist"));
        //now convert the product to product response
        ProductResponse productResponse = convertToProductResponse(product);
        log.info("Fetched Product by Id: {}", id);
        return productResponse;
    }


    @Override
    public List<ProductResponse> getAllProducts() {
        return List.of();
    }
    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .pictureUrl(product.getPictureUrl())
                .productType(product.getType().getName())
                .productBrand(product.getBrand().getName())
                .build();
    }
}
