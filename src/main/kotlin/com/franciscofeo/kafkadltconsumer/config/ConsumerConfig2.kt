package com.franciscofeo.kafkadltconsumer.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConsumerConfig2 {

    @Bean
    fun consumer2(): KafkaConsumer<String, String> =
        with(mutableMapOf<String, Any>()) {
            this[ConsumerConfig.GROUP_ID_CONFIG] = "my-consumer-gid-2"
            this[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:29091"
            this[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
            this[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
            this[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
            this[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = "false"
            this[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] = 2 // ONLY FOR TESTS, DEFAULT IS 500
            KafkaConsumer<String, String>(this)
        }
}