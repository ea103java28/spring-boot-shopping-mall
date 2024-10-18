package com.tony.dao;

import com.tony.model.Product;
import com.tony.repo.ProductRepository;
import com.tony.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {


    @Autowired
    private ProductRepository productRepo;

    @Override
    public List<Product> getProducts() {
        return (List<Product>) productRepo.findAll();
    }


    @Override
    public Product getProductById(Integer productId) {
        return productRepo.findById(productId).get();
    }

    @Override
    public Integer createProduct(Product product) {
        return productRepo.save(product).getProductId();
    }

    @Override
    public void updateProduct(Integer productId, Product product) {
        product.setProductId(productId);
        productRepo.save(product);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepo.deleteById(productId);
    }
}
