package com.kafka.training.demo1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.streams.StreamsConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;

public class SimpleProducer {

    public static void main(String[] args) throws Exception {

        // Check arguments length value
        if (args.length == 0) {
            System.out.println("Enter topic name");
            return;
        }
        //Assign topicName to string variable
        String topicName = args[0];

        // create instance for properties to access producer configs
        Properties props = new Properties();
        //Assign localhost id
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //Set acknowledgements for producer requests.
        props.put("acks", "all");
        //If the request fails, the producer can automatically retry,
        props.put("retries", 0);
        //Specify buffer size in config
        props.put("batch.size", 16384);
        //Reduce the no of requests less than 0
        props.put("linger.ms", 1);
        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer(props);
        int i = 0;
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ProducerRecord record = new ProducerRecord(topicName, String.valueOf(i++));
            Future<RecordMetadata> future = producer.send(record);
            final RecordMetadata recordMetadata = future.get();
            System.out.println(String.format("\n\t\t\tkey=%s, value=%s " +
                            "\n\t\t\tsent to topic=%s part=%d off=%d at time=%s",
                    record.key(),
                    record.value(),
                    recordMetadata.topic(),
                    recordMetadata.partition(),
                    recordMetadata.offset(),
                    new Date(recordMetadata.timestamp())
            ));
        }
    }
}