package com.tony.scheduler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Stopwatch;
import com.tony.model.Product;
import com.tony.rabbitmq.topics.TopicsProducer;
//import com.tony.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

//@Component
//public class ProductScheduler {

//    @Autowired
//    TopicsProducer topicsProducer;

//    @Autowired
//    QueueService queueService;

//    Product product = null;


//    @Value("${redis.queue.key.name}")
//    private String productQueueKeyName;

//    long productQueueSize = 0L;


//    @Scheduled(fixedDelay = 5000)
//    public void productScheduler() throws JsonProcessingException {
//
//        productQueueSize = queueService.getQueueSize(productQueueKeyName);
//
////        Stopwatch stopwatch = Stopwatch.createStarted();
//
//        while (productQueueSize-- > 0) {
//            product = queueService.dequeueProduct();
//            topicsProducer.sendProduct(product);
//        }
//
////        long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
////        System.out.println("time: " + millis + " ms");
//    }
//
//}
