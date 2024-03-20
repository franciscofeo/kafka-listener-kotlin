package com.franciscofeo.kafkadltconsumer.usecases

import mu.KotlinLogging
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class ConsumeAllMessages(
    @Qualifier("consumer2")
    private val kafkaConsumer: KafkaConsumer<String, String>
) {

    fun consume(topic: String) {
        kafkaConsumer.subscribe(arrayListOf(topic))

        /**
         * Default config of max.poll.records is 500 records of each poll() call
         */
        val messages = kafkaConsumer.poll(Duration.ofSeconds(5))

        LOG.info("Messages in the topic: ${messages.count()}")

        LOG.info("-----------------------------------------------")
        messages.forEach {
            LOG.info("Message content: ${it.value()}")
            LOG.info("Message key: ${it.key()}")
            LOG.info("Message offset: ${it.offset()}")
            LOG.info("Message partition: ${it.partition()}")
        }
        LOG.info("-----------------------------------------------")

        kafkaConsumer.commitSync()
        kafkaConsumer.unsubscribe()
    }

    companion object {
        private val LOG = KotlinLogging.logger("consumer-logger")
    }
}