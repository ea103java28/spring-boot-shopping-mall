package com.tony.dao;


import com.tony.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(Product productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(Product productRequest);

    void updateProduct(Integer productId, Product productRequest);

    void updateStock(Integer productId, Integer stock);

    void deleteProduct(Integer productId);
}
