package com.tony.rabbitmq.topics;

import com.tony.model.Product;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TopicsProducer {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    @Value("${t.exchange.p}")
    private String topicsExchangeProduct;

    String routingKey = null;


    public void ProducerProduct(Product product) {
        routingKey = product.getCategory().toString();
        rabbitmqTemplate.convertAndSend(topicsExchangeProduct, routingKey, product);
    }


}
