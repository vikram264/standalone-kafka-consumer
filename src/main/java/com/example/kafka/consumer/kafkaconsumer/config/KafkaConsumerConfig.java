package com.example.kafka.consumer.kafkaconsumer.config;

import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;

import com.example.kafka.consumer.kafkaconsumer.bean.Employee;
import com.example.kafka.consumer.kafkaconsumer.intializer.KafkaJsonDeserializer;
import com.fasterxml.jackson.databind.JsonDeserializer;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class KafkaConsumerConfig
{

    public Consumer<String, Employee> createConsumer ()
    {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "rta-group-id");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class
            .getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class
            );

        Consumer<String, Employee> consumer = new KafkaConsumer<>(props, new StringDeserializer(), new KafkaJsonDeserializer<Employee>(Employee.class));
        consumer.subscribe(Pattern.compile("rta.*"));
        return consumer;

    }

    public void runConsumer () throws InterruptedException
    {

        log.info("Running the consumer...");
        Consumer<String, Employee> consumer = createConsumer();
        try {
            while (true) {
                
                ConsumerRecords<String, Employee> records = consumer.poll(100);
                for (ConsumerRecord<String, Employee> record : records) {
                    log.info("key ::" + record.key());
                    log.info("value ::" + record.value());
                    log.info("topic ::" + record.topic());
                    log.info("partition ::" + record.partition());
                    log.info("offset ::" + record.offset());
                    
                }
                consumer.commitAsync();
            }

        }
        finally {
            consumer.close();
        }

    }
}
