package com.kafka.training.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Producer {
        public static void main(String[] argv)throws Exception {
            if (argv.length != 1) {
                System.err.println("Please specify 1 parameter ");
                System.exit(-1);
            }
            String topicName = argv[0];

            System.out.println("Enter message(type exit to quit)");

            //Configure the Producer
            Properties configProperties = new Properties();
            configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerSerializer");
            configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

            KafkaProducer<Integer, String> producer = new KafkaProducer<>(configProperties);
            for (int i = 1; i <= 10; i++) {
                ProducerRecord<Integer, String> rec = new ProducerRecord<>(topicName, i, String.valueOf(i));
                producer.send(rec);
                Thread.sleep(100);
            }
            producer.close();
        }
    }

