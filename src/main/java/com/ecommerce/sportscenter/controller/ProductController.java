package com.ecommerce.sportscenter.controller;

import com.ecommerce.sportscenter.model.BrandResponse;
import com.ecommerce.sportscenter.model.ProductResponse;
import com.ecommerce.sportscenter.model.TypeResponse;
import com.ecommerce.sportscenter.service.BrandService;
import com.ecommerce.sportscenter.service.ProductService;
import com.ecommerce.sportscenter.service.TypeService;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService  productService;
    private final TypeService typeService;
    private final BrandService brandService;

    public ProductController(ProductService productService, TypeService typeService, BrandService brandService) {
        this.productService = productService;
        this.typeService = typeService;
        this.brandService = brandService;
    }
    @GetMapping("/{id}") //path variable
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Integer productId){
        ProductResponse  productResponse =productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @PageableDefault(size = 10)Pageable pageable,
            @RequestParam(name="s",required = false) String s,
            @RequestParam(name="brandId",required = false) Integer brandId,
            @RequestParam(name="typeId",required = false) Integer typeId,
            @RequestParam(name="sort",defaultValue = "name") String sort,
            @RequestParam(name="order",defaultValue = "asc") String order
            ){
        Page<ProductResponse> productResponsePage;
        if(brandId!=null && typeId!=null && !s.isEmpty()) {
            List<ProductResponse> productResponses = productService.searchProductsByByBrandTypeAndName(brandId,typeId,s);
            productResponsePage = new PageImpl<>(productResponses, pageable, productResponses.size());
        } else if (s!=null && typeId!=null && !s.isEmpty()){
            List<ProductResponse> productResponses = productService.searchProductsByByBrandType(brandId,typeId);
            productResponsePage =new PageImpl<>(productResponses,pageable,productResponses.size());
        }
        else if(brandId!=null) {
            //search by brand
            List<ProductResponse> productResponses = productService.searchProductsByBrand(brandId);
            productResponsePage = new PageImpl<>(productResponses, pageable, productResponses.size());
        }
        else if(typeId!=null) {
            //search by type
            List<ProductResponse> productResponses = productService.searchProductsByType(typeId);
            productResponsePage = new PageImpl<>(productResponses, pageable, productResponses.size());
        }else{
            //if no such criteria return based on sorting option
            Sort.Direction direction ="asc".equalsIgnoreCase(order)?Sort.Direction.ASC:Sort.Direction.DESC;
            Sort sorting=Sort.by(direction,sort);

            productResponsePage=productService.getAllProducts(PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),sorting));
        }
        return new ResponseEntity<>(productResponsePage,HttpStatus.OK);
//        Page<ProductResponse> productResponsePage=productService.getAllProducts(pageable);
//        return new ResponseEntity<>(productResponsePage,HttpStatus.OK);
    }
    @GetMapping("/brands")
    public ResponseEntity<List<BrandResponse>> getAllBrands(){
        List<BrandResponse> brandResponses =brandService.getAllBrands();
        return new ResponseEntity<>(brandResponses,HttpStatus.OK);
    }
    @GetMapping("/types")
    public ResponseEntity<List<TypeResponse>> getAllTypes(){
        List<TypeResponse> typeResponses =typeService.getAllTypes();
        return new ResponseEntity<>(typeResponses,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam("s") String s){
        List<ProductResponse> productResponses  =productService.searchProductsByName(s);
        return new ResponseEntity<>(productResponses,HttpStatus.OK);
    }

}
