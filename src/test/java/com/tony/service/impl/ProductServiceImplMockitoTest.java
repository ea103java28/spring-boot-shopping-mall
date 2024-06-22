package com.tony.service.impl;

import com.tony.constant.ProductCategory;
import com.tony.dao.ProductDao;
import com.tony.model.Product;
import com.tony.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplMockitoTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductDao productDao; // spring 容器裡面的 ProductDao 就會被替換成我們假的 bean，這時候我們就能自由的去指定我們要返回甚麼值


    /*
    當我們這樣寫的話
    就是說 在每個 test case 執行之前
    都會去執行一次這個 beforeEach 執行方法
    去設定這個假的 productDao 的返回值
     */
    @BeforeEach
    public void beforeEach(){
        Product mockProduct = new Product();
        mockProduct.setProductId(1);
        mockProduct.setProductName("I am mock");
        mockProduct.setCategory(ProductCategory.CAR);
        Mockito.when(productDao.getProductById(Mockito.any())).thenReturn(mockProduct);
    }

    @Test
    void getProducts() {
    }

    @Test
    void countProduct() {
    }

    @Test
    void getProductById() {

//        Product mockProduct = new Product();
//        mockProduct.setProductId(1);
//        mockProduct.setProductName("I am mock");
//        mockProduct.setCategory(ProductCategory.CAR);

        // 這段很重要!! 當有人去 call productDao.getProductById(1) 這個方法，就會固定去返回 mockProduct 這個 object
//        Mockito.when(productDao.getProductById(1)).thenReturn(mockProduct);
        // 使用 Mockito.any() 把這個方法參數值定義比較寬鬆點
//        Mockito.when(productDao.getProductById(Mockito.any())).thenReturn(mockProduct);

        Product product = productService.getProductById(1);
        assertNotNull(product);
        assertEquals(product.getProductId(), 1);
        assertEquals(product.getProductName(), "I am mock");
        assertEquals(product.getCategory(), ProductCategory.CAR);

    }

    /*
    getProductById() 這種作法不是不可以
    只是會讓單元測試變得很冗長，後續變得比較難維護
    所以可以使用 @BeforeEach 註解
    可以在每個 test case 執行之前
    都去執行 mock bean 的設定
     */

    @Test
    void getProductById2() {


        Product product = productService.getProductById(1);
        assertNotNull(product);
        assertEquals(product.getProductId(), 1);
        assertEquals(product.getProductName(), "I am mock");
        assertEquals(product.getCategory(), ProductCategory.CAR);

    }

    @Test
    void createProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}