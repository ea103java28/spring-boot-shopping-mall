package com.tony.service;

import com.tony.dao.ProductDao;
import com.tony.model.Product;
import com.tony.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts() {
        return productDao.getProducts();
    }


    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(Product product) {
        return productDao.createProduct(product);
    }

    @Override
    public void updateProduct(Integer productId, Product product) {
     productDao.updateProduct(productId, product);
    }

    @Override
    public void deleteProduct(Integer productId) {
       productDao.deleteProduct(productId);
    }
}
