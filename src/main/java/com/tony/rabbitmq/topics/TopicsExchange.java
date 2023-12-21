package com.tony.rabbitmq.topics;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TopicsExchange {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    @Value("${t.exchange.p}")
    private String topicsExchangeProduct;


    @Value("${queue.p.food}")
    private String queueProductFood;

    @Value("${queue.p.car}")
    private String queueProductCar;

    @Value("${queue.p.ebook}")
    private String queueProductEbook;


    @Bean
    public TopicExchange topicsExchangeProduct() {
        return new TopicExchange(topicsExchangeProduct);
    }


    @Bean
    public Binding bindingProductFood(TopicExchange topicsExchangeProduct, Queue queueProductFood) {
        return BindingBuilder.bind(queueProductFood).to(topicsExchangeProduct).with("#.FOOD.#");
    }

    @Bean
    public Binding bindingProductCar(TopicExchange topicsExchangeProduct, Queue queueProductCar) {
        return BindingBuilder.bind(queueProductCar).to(topicsExchangeProduct).with("#.CAR.#");
    }

    @Bean
    public Binding bindingProductEbook(TopicExchange topicsExchangeProduct, Queue queueProductEbook) {
        return BindingBuilder.bind(queueProductEbook).to(topicsExchangeProduct).with("#.E_BOOK.#");
    }


}
