package com.tony.dao.impl;

import com.tony.constant.ServiceBeanConstants;
import com.tony.dao.ProductDao;
import com.tony.dto.ProductQueryParams;
import com.tony.dto.ProductRequest;
import com.tony.model.Product;
import com.tony.repository.ProductRepository;
import com.tony.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext(unitName = ServiceBeanConstants.MSSQL_ENTITY_MGR_MALL)
    private EntityManager entityManager;

    @Transactional
    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {

        String sql "select e from empoylee e";
       TypedQuery<XXX> typedQuery = entityManager.createQuery(sql, XXX.class);
       typedQuery.setParameter("xxx", xxx);
       typedQuery.getResultList();

        String sql2 "select * from eee_xxx";
        Query typedQuery2 = entityManager.createNativeQuery(sql2);
        List<Object> objs = typedQuery2.getResultList();
        List<Empolyee> empolyeeList = getContactResult(objs);




        String sql = "SELECT product_id, product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date " +
                "FROM product WHERE 1 = 1";

        Map<String, Object> map = new HashMap<>();

        // 查詢條件
        sql = addFilteringSql(sql, map, productQueryParams);

        // 排序
        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        // 分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }

    private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {

        if (productQueryParams.getCategory() != null) {
            sql = sql + " AND category = :category";
            map.put("category", productQueryParams.getCategory().name());
        }

        if (productQueryParams.getSearch() != null) {
            sql = sql + " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        return sql;
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {

        String sql = "SELECT count(*) FROM product WHERE 1 = 1";

        Map<String, Object> map = new HashMap<>();

        // 查詢條件
        sql = addFilteringSql(sql, map, productQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public Product getProductById(Integer productId) {

        Product product = productRepository.findById(productId).orElse(null);
        return product;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        Product product = productRequestToProduct(productRequest);
        Product savedProduct = productRepository.save(product);
        return savedProduct.getProductId();
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {


            Product product = productRequestToProduct(productRequest);
            product.setProductId(productId);
            productRepository.save(product);

    }

    @Override
    public void updateStock(Integer productId, Integer stock) {

        Product product = productRepository.findById(productId).orElse(null);
        if(product == null){
            return;
        }else{
            product.setStock(stock);
            productRepository.save(product);
        }
    }

    @Override
    public void deleteProduct(Integer productId) {

        productRepository.deleteById(productId);
    }


    private Product productRequestToProduct(ProductRequest productRequest){

        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStock(productRequest.getStock());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        return product;

    }

}
