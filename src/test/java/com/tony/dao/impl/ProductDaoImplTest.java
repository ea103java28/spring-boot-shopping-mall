package com.tony.dao.impl;

import com.tony.constant.ProductCategory;
import com.tony.model.Product;
import com.tony.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductDaoImplTest {


    @Autowired
    private ProductRepository productRepository;

    @Test
    void getProducts() {
    }

    @Test
    void countProduct() {
    }

    @DisplayName("讀取 product")
    @Test
     void getProductById() {
        Product product = productRepository.findById(1).orElse(null);
        assertNotNull(product);
        assertEquals(product.getProductId(), 1);
        assertEquals(product.getProductName(), "蘋果");
        assertEquals(product.getCategory(), ProductCategory.FOOD);
    }

    @Test
    void createProduct() {
    }

    @Test
    @Transactional
    @DisplayName("更新 product")
    void updateProduct() {
        Product product = new Product();
        product.setProductId(777);
        product.setProductName("香蕉");
        product.setCategory(ProductCategory.CAR);
        product.setStock(959);
        productRepository.save(product);

        Product product2 = productRepository.findById(777).orElse(null);
        assertNotNull(product2);
        assertEquals(product2.getProductId(), 777);
        assertEquals(product2.getProductName(), "香蕉");
        assertEquals(product2.getCategory(), ProductCategory.CAR);
        assertEquals(product2.getStock(), 959);
    }

    @Test
    void updateStock() {
    }

    @Test
    @Transactional
    void deleteProduct() {
    productRepository.deleteById(777);
    Product product = productRepository.findById(777).orElse(null);
    assertNull(product);
    }
}