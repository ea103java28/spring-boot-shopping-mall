package com.tony.repo;

import com.tony.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query(value = "select p.product_id, p.category, o.amount, p.last_modified_date from product p, order_item o where p.product_id = o.product_id;", nativeQuery = true)
    List<ProductDto> findAllProductDto();

    public interface ProductDto{
        String getProduct_id();
        String getCategory();
        Integer getAmount();
        String getLast_modified_date();
        default String getLast_modified_date_format() throws ParseException, ParseException {
            SimpleDateFormat sdf_yyyyMMddHHmmssSSS= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
            return getLast_modified_date() != null ? sdf_yyyyMMdd.format(sdf_yyyyMMddHHmmssSSS.parse(getLast_modified_date())) : null;
        }
    }
}
