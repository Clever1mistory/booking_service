package com.example.booking_service.config;

import com.example.booking_service.event.BookingEvent;
import com.example.booking_service.event.UserRegistrationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${app.kafka.groupId}")
    private String groupId;

    @Bean
    public ProducerFactory<String, UserRegistrationEvent> producerFactory(ObjectMapper objectMapper) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), new JsonSerializer<>(objectMapper));
    }

    @Bean
    public KafkaTemplate<String, UserRegistrationEvent> kafkaTemplate(ProducerFactory<String, UserRegistrationEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, BookingEvent> orderStatusEventProducerFactory(ObjectMapper objectMapper) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), new JsonSerializer<>(objectMapper));
    }

    @Bean
    public KafkaTemplate<String, BookingEvent> orderStatusEventKafkaTemplate(ProducerFactory<String, BookingEvent> orderStatusEventProducerFactory) {
        return new KafkaTemplate<>(orderStatusEventProducerFactory);
    }

    @Bean
    public ConsumerFactory<String, UserRegistrationEvent> userRegistrationEventConsumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(UserRegistrationEvent.class, objectMapper));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserRegistrationEvent> userRegistrationEventKafkaListenerContainerFactory(
            ConsumerFactory<String, UserRegistrationEvent> userRegistrationEventConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, UserRegistrationEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userRegistrationEventConsumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, BookingEvent> roomBookingEventConsumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(BookingEvent.class, objectMapper));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookingEvent> roomBookingEventKafkaListenerContainerFactory(
            ConsumerFactory<String, BookingEvent> roomBookingEventConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, BookingEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(roomBookingEventConsumerFactory);
        return factory;
    }

}
