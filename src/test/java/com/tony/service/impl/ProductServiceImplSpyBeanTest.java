package com.tony.service.impl;

import com.tony.constant.ProductCategory;
import com.tony.dao.ProductDao;
import com.tony.dto.ProductRequest;
import com.tony.model.Product;
import com.tony.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplSpyBeanTest {

    @Autowired
    private ProductService productService;

    @SpyBean
    private ProductDao productDao;

    @Test
    void getProducts() {

    }

    @Test
    void countProduct() {
    }

    @Test
    void getProductById() {
        //去 call 真實的那一個方法
        Product product = productService.getProductById(3);
        assertNotNull(product);
        assertEquals(product.getProductId(), 3);
        assertEquals(product.getProductName(), "蘋果（日本北海道）");
        assertEquals(product.getCategory(), ProductCategory.FOOD);
        assertEquals(product.getPrice(), 300);

    }


    @Test
    void createProduct() {

        // 使用假的 spyBean 去 return
        Mockito.doReturn(777).when(productDao).createProduct(Mockito.any());

        ProductRequest product = new ProductRequest();
        product.setProductId(777);
        product.setProductName("香蕉");
        product.setCategory(ProductCategory.FOOD);
        product.setStock(959);
        Integer productId = productService.createProduct(product);
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}