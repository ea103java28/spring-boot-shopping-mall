package com.tony.rabbitmq.topics;

import com.tony.model.Product;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicsConsumer {

    @RabbitListener(queues = "${queue.p.food}")
    public void consumerProductFood(Product product) {
    }


    @RabbitListener(queues = "${queue.p.car}")
    public void consumerProductCar(Product product) {
    }


    @RabbitListener(queues = "${queue.p.ebook}")
    public void consumerProductEbook(Product product) {
    }

}
