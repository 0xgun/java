package com.ecommerce.sportscenter.service;

import com.ecommerce.sportscenter.entity.Product;
import com.ecommerce.sportscenter.model.ProductResponse;
import com.ecommerce.sportscenter.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        log.info("Fetching all products");
        //retrive data from db
        Page<Product>  productPage = productRepository.findAll(pageable);
        //map
        Page<ProductResponse> productResponses =productPage
                .map(this::convertToProductResponse);
        log.info("Fetched all products");
        return productResponses;
    }

    @Override
    public List<ProductResponse> searchProductsByName(String s) {
        log.info("Searching products by name {}", s);
        //custom query method
        List<Product> products = productRepository.searchByName(s);
        //Map
        List<ProductResponse> productResponses = products.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
        return productResponses;
    }

    @Override
    public List<ProductResponse> searchProductsByByBrandTypeAndName(Integer brandId, Integer typeId, String s) {
        log.info("Searching products by BrandID :{} and TypeID :{} and name:{}", brandId,typeId,s);
        //custom query method
        List<Product> products = productRepository.searchByBrandTypeAndName(brandId,typeId,s);
        //Map
        List<ProductResponse> productResponses = products.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
        log.info("Fetched all products");

        return productResponses;
    }

    @Override
    public List<ProductResponse> searchProductsByByBrandType(Integer brandId, Integer typeId) {
        log.info("Searching products by BrandID : {} and TypeID : {}", brandId,typeId);
        //custom query method
        List<Product> products = productRepository.searchByBrandAndType(brandId,typeId);
        //Map
        List<ProductResponse> productResponses = products.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
        log.info("Fetched all products");

        return productResponses;
    }

    @Override
    public List<ProductResponse> searchProductsByBrand(Integer brandId) {
        log.info("Searching product(s) by brandId: {}", brandId);
        //Call the custom query Method
        List<Product> products = productRepository.searchByBrand(brandId);
        //Map
        List<ProductResponse> productResponses = products.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
        log.info("Fetched all products");
        return productResponses;
    }

    @Override
    public List<ProductResponse> searchProductsByType(Integer typeId) {
        log.info("Searching product(s) by typeId: {}", typeId);
        //Call the custom query Method
        List<Product> products = productRepository.searchByType(typeId);
        //Map
        List<ProductResponse> productResponses = products.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
        log.info("Fetched all products");
        return productResponses;
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
