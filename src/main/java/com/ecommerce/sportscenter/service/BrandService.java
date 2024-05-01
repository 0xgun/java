package com.ecommerce.sportscenter.service;

import com.ecommerce.sportscenter.entity.Brand;
import com.ecommerce.sportscenter.model.BrandResponse;

import java.util.List;

public interface BrandService {
    List<BrandResponse> getAllBrands();
}
