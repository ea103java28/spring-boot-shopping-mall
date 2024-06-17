package com.tony.rabbitmq.topics;

import com.google.common.base.Stopwatch;
import com.tony.dto.ProductRequest;
import com.tony.model.Product;
import com.tony.service.ProductService;
import com.tony.service.QueueService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/*
RabbitListener annoation is async, so not references the same object,otherwise would be broken.
 */
@Component
@PropertySource("classpath:rabbitmq.properties")
public class TopicsConsumer {

    @Autowired
    ProductService productService;

    @Autowired
    QueueService queueService;


    @RabbitListener(queues = {"${queue.p.food}", "${queue.p.car}", "${queue.p.ebook}"})
    public void consumeProduct(Product product) {
        ProductRequest productRequest = productToProductRequest(product);
        int productId = productService.createProduct(productRequest);
//        System.out.println("Consumer for category " + product.getCategory() + ":\t" + productService.getProductById(productId).toString());
    }


    private ProductRequest productToProductRequest(Product product) {

        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName(product.getProductName());
        productRequest.setCategory(product.getCategory());
        productRequest.setDescription(product.getDescription());
        productRequest.setImageUrl(product.getImageUrl());
        productRequest.setPrice(product.getPrice());
        productRequest.setStock(product.getStock());

        return productRequest;
    }

}
