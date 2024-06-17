package com.tony.rabbitmq.topics;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:rabbitmq.properties")
public class TopicsExchange {


    @Value("${t.exchange.p}")
    private String topicsExchangeProductName;


    @Value("${queue.p.food}")
    private String queueProductFoodName;

    @Value("${queue.p.car}")
    private String queueProductCarName;

    @Value("${queue.p.ebook}")
    private String queueProductEbookName;


    @Autowired
    private AmqpTemplate rabbitmqTemplate;


    @Bean
    public TopicExchange topicsExchangeProduct() {
        return new TopicExchange(topicsExchangeProductName);
    }

    @Bean
    public Queue queueProductFood() {
        return new Queue(queueProductFoodName);
    }

    @Bean
    public Queue queueProductCar() {
        return new Queue(queueProductCarName);
    }

    @Bean
    public Queue queueProductEbook() {
        return new Queue(queueProductEbookName);
    }


    @Bean
    public Binding bindingProductFood(TopicExchange topicsExchangeProduct, Queue queueProductFood, @Value("${binding.pattern.food}") String bindingPattern) {
        return BindingBuilder.bind(queueProductFood).to(topicsExchangeProduct).with(bindingPattern);
    }

    @Bean
    public Binding bindingProductCar(TopicExchange topicsExchangeProduct, Queue queueProductCar, @Value("${binding.pattern.car}") String bindingPattern) {
        return BindingBuilder.bind(queueProductCar).to(topicsExchangeProduct).with(bindingPattern);
    }

    @Bean
    public Binding bindingProductEbook(TopicExchange topicsExchangeProduct, Queue queueProductEbook, @Value("${binding.pattern.ebook}") String bindingPattern) {
        return BindingBuilder.bind(queueProductEbook).to(topicsExchangeProduct).with(bindingPattern);
    }


}
