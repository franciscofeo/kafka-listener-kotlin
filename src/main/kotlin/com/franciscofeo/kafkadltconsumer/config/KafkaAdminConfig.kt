package com.franciscofeo.kafkadltconsumer.config

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaAdminConfig {

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs = mutableMapOf<String, Any>()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:29091"
        return KafkaAdmin(configs)
    }

    @Bean
    fun adminClient(kafkaAdmin: KafkaAdmin): AdminClient =  AdminClient.create(kafkaAdmin.configurationProperties)
}