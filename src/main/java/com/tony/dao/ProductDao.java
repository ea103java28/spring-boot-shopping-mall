package com.tony.dao;


import com.tony.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();

    Product getProductById(Integer productId);

    Integer createProduct(Product product);

    void updateProduct(Integer productId, Product product);

    void deleteProduct(Integer productId);
}
