package com.git.judice.inventory.config.kafka;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.git.judice.inventory.adapters.out.message.SaleMessage;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  @Bean
  ConsumerFactory<String, SaleMessage> consumerFactory() {

    Map<String, Object> props = new HashMap<>();
    props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    // props.put(VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(VALUE_DESERIALIZER_CLASS_CONFIG, CustomDeserializer.class);

    props.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
    // props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.git.judice.*");
    return new DefaultKafkaConsumerFactory<>(props);

  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, SaleMessage> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, SaleMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.setCommonErrorHandler(new KafkaErrorHandler());

    return factory;
  }

}