package com.tony.controller;
import com.tony.constant.ServiceBeanConstants;
import com.tony.model.MallDto;
import com.tony.model.Product;
import com.tony.repo.ProductRepository;
import com.tony.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/products")
@CrossOrigin()
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    @Qualifier(ServiceBeanConstants.MSSQL_DATASOURC_JDBC_TEMPLETE_MALL)
    JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts(){

       return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {

        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {

        Integer productId = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.getProductById(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid Product product) {

        Product product2 = productService.getProductById(productId);
        if (product2 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.updateProduct(productId, product);
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        productService.deleteProduct(productId);
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();

    }


    @GetMapping("/findAllProductDto")
    public List<ProductRepository.ProductDto> findAllProductDto(){
        return  productRepo.findAllProductDto()
                .stream()
//                .sorted(Comparator.comparing(ProductRepository.ProductDto::getLast_modified_date))
                .sorted(Comparator.comparing(productDto -> {
                    try {
                        return productDto.getLast_modified_date_format();
                    } catch (ParseException e) {
                        // 在出現例外時，可以返回 null 或進行其他處理
                        return null;
                    } 
                }))
                .collect(Collectors.toList());
    }

    @GetMapping("/findMallDtoById/{product_id}")
    public List<MallDto> findMallDtoByOrderId(@PathVariable Integer product_id){
    String sql = "select p.product_id, p.category, o.amount, p.last_modified_date" +
            "  from product p, order_item o " +
            "where p.product_id = o.product_id  and p.product_id = ? ";

        List<MallDto> result = new ArrayList<>();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, product_id);
        System.out.println(list.size());
        for (Map m : list) {
            MallDto mallDto = new MallDto();
            mallDto.setProduct_id((Integer)      m.get("product_id"));
            mallDto.setCategory((String)         m.get("category"));
            mallDto.setAmount((Integer)          m.get("amount"));
            mallDto.setLast_modified_date((Date) m.get("last_modified_date"));
            result.add(mallDto);
        }
        return result;

    }


}
