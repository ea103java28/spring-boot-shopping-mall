package com.tony.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tony.constant.ProductCategory;
import com.tony.dto.ProductQueryParams;
import com.tony.dto.ProductRequest;
import com.tony.model.Product;
import com.tony.service.ProductService;
//import com.tony.service.QueueService;
import com.tony.util.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/products")
@CrossOrigin()
public class ProductController {

    @Autowired
    private ProductService productService;

//    @Autowired
//    private QueueService queueService;

    @Operation(
            summary = "Query Product List",
            description = "query condition by category, search, orderBy, sort, limit, offset",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get page of product info successfully"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "sever error",
                            content = @Content
                    )
            }
    )
    @GetMapping("/")
    public ResponseEntity<Page<Product>> getProducts(
            // 查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            // 排序 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            // 分頁 Pagination
            @RequestParam(defaultValue = "10000") @Max(10000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset) {

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        // 取得product list
        List<Product> productList = productService.getProducts(productQueryParams);

        // 取得不同條件下的product總數
        Integer total = productService.countProduct(productQueryParams);

        // 分頁response的值
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@Parameter(description = "id of product") @PathVariable Integer productId) {
        Product product = productService.getProductById(productId);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

//    @PostMapping("/enqueue")
//    public ResponseEntity enqueueProductToRedis(@RequestBody @Valid ProductRequest productRequest) throws JsonProcessingException {
//        queueService.enqueueProduct(productRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {

        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        Product product = productService.getProductById(productId);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productService.updateProduct(productId, productRequest);

        Product updatedProduct = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
