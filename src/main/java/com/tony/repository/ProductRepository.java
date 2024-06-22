package com.tony.repository;

import com.tony.constant.ProductCategory;
import com.tony.dto.ProductQueryParams;
import com.tony.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {


}
