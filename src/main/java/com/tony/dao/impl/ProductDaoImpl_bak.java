//package com.tony.dao.impl;
//
//import com.tony.dto.ProductQueryParams;
//import com.tony.dto.ProductRequest;
//import com.tony.model.Product;
//import com.tony.rowmapper.ProductRowMapper;
//import com.tony.dao.ProductDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class ProductDaoImpl_bak implements ProductDao {
//
//    @Autowired
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    @Override
//    public List<Product> getProducts(ProductQueryParams productQueryParams) {
//        String sql = "SELECT product_id, product_name, category, image_url, price, stock, " +
//                "description, created_date, last_modified_date " +
//                "FROM product WHERE 1 = 1";
//
//        Map<String, Object> map = new HashMap<>();
//
//        // 查詢條件
//        sql = addFilteringSql(sql, map, productQueryParams);
//
//        // 排序
//        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();
//
//        // 分頁
//        sql = sql + " LIMIT :limit OFFSET :offset";
//        map.put("limit", productQueryParams.getLimit());
//        map.put("offset", productQueryParams.getOffset());
//
//        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
//
//        return productList;
//    }
//
//    @Override
//    public Integer countProduct(ProductQueryParams productQueryParams) {
//
//        String sql = "SELECT count(*) FROM product WHERE 1 = 1";
//
//        Map<String, Object> map = new HashMap<>();
//
//        // 查詢條件
//        sql = addFilteringSql(sql, map, productQueryParams);
//
//        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
//
//        return total;
//    }
//
//    @Override
//    public Product getProductById(Integer productId) {
//        String sql = "SELECT product_id, product_name, category, image_url, price, stock, " +
//                "description, created_date, last_modified_date " +
//                "FROM product WHERE product_id = :product_id";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("product_id", productId);
//
//        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
//
//        if (productList.isEmpty()) {
//            return null;
//        }
//
//        return productList.get(0);
//    }
//
//    @Override
//    public Integer createProduct(ProductRequest productRequest) {
//        String sql = "INSERT INTO product (product_name, category, image_url, " +
//                "price, stock, description) " +
//                "VALUES (:productName, :category, :imageUrl, :price, :stock, " +
//                ":description)";
//
//        Map<String, Object> map = parseProductRequest(productRequest);
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
//
//        Integer productId = keyHolder.getKey().intValue();
//
//        return productId;
//    }
//
//    @Override
//    public void updateProduct(Integer productId, ProductRequest productRequest) {
//        String sql = "UPDATE product SET product_name = :productName, " +
//                "category = :category, image_url = :imageUrl, price = :price, " +
//                "stock = :stock, description = :description " +
//                "WHERE product_id = :productId";
//
//        Map<String, Object> map = parseProductRequest(productRequest);
//        map.put("productId", productId);
//
//        namedParameterJdbcTemplate.update(sql, map);
//
//    }
//
//    @Override
//    public void updateStock(Integer productId, Integer stock) {
//        String sql = "UPDATE product SET stock = :stock WHERE product_id = :productId";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("productId", productId);
//        map.put("stock", stock);
//
//        namedParameterJdbcTemplate.update(sql, map);
//    }
//
//    @Override
//    public void deleteProduct(Integer productId) {
//        String sql = "DELETE FROM product WHERE product_id = :productId";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("productId", productId);
//
//        namedParameterJdbcTemplate.update(sql, map);
//    }
//
//    private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {
//
//        if (productQueryParams.getCategory() != null) {
//            sql = sql + " AND category = :category";
//            map.put("category", productQueryParams.getCategory().name());
//        }
//
//        if (productQueryParams.getSearch() != null) {
//            sql = sql + " AND product_name LIKE :search";
//            map.put("search", "%" + productQueryParams.getSearch() + "%");
//        }
//
//        return sql;
//    }
//
//    private Map<String, Object> parseProductRequest(ProductRequest productRequest) {
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("productName", productRequest.getProductName());
//        map.put("category", productRequest.getCategory().toString());
//        map.put("imageUrl", productRequest.getImageUrl());
//        map.put("price", productRequest.getPrice());
//        map.put("stock", productRequest.getStock());
//        map.put("description", productRequest.getDescription());
//
//        return map;
//    }
//}
