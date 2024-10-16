package com.tony.repository.impl;

import com.tony.dto.ProductQueryParams;
import com.tony.model.Product;
import com.tony.repository.ProductRepositoryCustom;

import org.springframework.stereotype.Repository;

import jakarta.persistence.*;
import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

//    @PersistenceContext
//    private EntityManager entityManager;

    @Override
    public List<Product> getProducts(ProductQueryParams params) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("SELECT p FROM Product p WHERE 1=1");
//
//        if (params.getCategory() != null) {
//            sb.append(" AND p.category = :category");
//        }
//        if (params.getSearch() != null && !params.getSearch().isEmpty()) {
//            sb.append(" AND p.productName LIKE :search");
//        }
//        if (params.getOrderBy() != null && !params.getOrderBy().isEmpty()) {
//            sb.append(" ORDER BY p.").append(params.getOrderBy());
//            if (params.getSort() != null && params.getSort().equalsIgnoreCase("desc")) {
//                sb.append(" DESC");
//            } else {
//                sb.append(" ASC");
//            }
//        }
//
//        TypedQuery<Product> query = entityManager.createQuery(sb.toString(), Product.class);
//
//        if (params.getCategory() != null) {
//            query.setParameter("category", params.getCategory());
//        }
//        if (params.getSearch() != null && !params.getSearch().isEmpty()) {
//            query.setParameter("search", "%" + params.getSearch() + "%");
//        }
//        if (params.getLimit() != null) {
//            query.setMaxResults(params.getLimit());
//        }
//        if (params.getOffset() != null) {
//            query.setFirstResult(params.getOffset());
//        }
//
//        return query.getResultList();


    EntityManager em = Persistence.createEntityManagerFactory("mall").createEntityManager();
    String jpql = "SELECT p FROM product p";
    Query query = em.createQuery(jpql, Product.class);
    List<Product> ProductList = query.getResultList();
    return ProductList;
    }
}
