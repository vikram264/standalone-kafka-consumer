package com.example.kafka.consumer.kafkaconsumer.intializer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kafka.consumer.kafkaconsumer.config.KafkaConsumerConfig;

@Service
public class KafkaInitializer
{

   @Autowired
   KafkaConsumerConfig kafkaConfig;
   
   @PostConstruct
   public void init() {
      try {
        kafkaConfig.runConsumer();
    }
    catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } 
   }
    
}
