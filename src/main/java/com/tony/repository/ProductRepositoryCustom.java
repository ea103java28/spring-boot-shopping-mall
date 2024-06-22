package com.tony.repository;

import com.tony.dto.ProductQueryParams;
import com.tony.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getProducts(ProductQueryParams productQueryParams);
}
