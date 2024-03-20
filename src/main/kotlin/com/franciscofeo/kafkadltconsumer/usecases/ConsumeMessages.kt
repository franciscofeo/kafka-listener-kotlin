package com.franciscofeo.kafkadltconsumer.usecases

import mu.KotlinLogging
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.stereotype.Component
import java.time.Duration.ofSeconds

@Component
class ConsumeMessages(
    private val kafkaConsumer: KafkaConsumer<String, String>
) {

    fun consume(topic: String) {
        kafkaConsumer.subscribe(listOf(topic))

        val messages = kafkaConsumer.poll(ofSeconds(5))

        LOG.info("Messages in the topic: ${messages.count()}")

        messages.forEach {
            LOG.info("Message content: ${it.value()}")
            LOG.info("Message key: ${it.key()}")
            LOG.info("Message partition: ${it.partition()}")
        }

        kafkaConsumer.commitSync()
        kafkaConsumer.unsubscribe()
    }

    companion object {
        private val LOG = KotlinLogging.logger("consume-logger")
    }
}

