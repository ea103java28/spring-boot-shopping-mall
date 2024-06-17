package com.tony.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.dto.ProductRequest;
import com.tony.model.Product;
import com.tony.service.QueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@PropertySource("classpath:redis.properties")
@Service
public class QueueServiceImpl implements QueueService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static Logger logger = LoggerFactory.getLogger(QueueService.class);
    ObjectMapper objectMapper = new ObjectMapper();
    String json = null;


    @Value("${redis.queue.key.name}")
    private String productQueueKey;


    @Override
    public void enqueueProduct(ProductRequest productRequest) throws JsonProcessingException {
        json = objectMapper.writeValueAsString(productRequest);
        redisTemplate.opsForList().leftPush(productQueueKey, json);
    }

    @Override
    public Product dequeueProduct() throws JsonProcessingException {
        json = redisTemplate.opsForList().rightPop(productQueueKey).toString();
        return objectMapper.readValue(json, Product.class);
    }

    @Override
    public long getQueueSize(String key) {
        return redisTemplate.opsForList().size(key);
    }
}
