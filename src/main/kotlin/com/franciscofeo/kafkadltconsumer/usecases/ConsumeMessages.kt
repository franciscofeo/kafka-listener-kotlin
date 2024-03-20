package com.franciscofeo.kafkadltconsumer.usecases

import mu.KotlinLogging
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.stereotype.Component
import java.time.Duration.ofSeconds

@Component
class ConsumeMessages(
    private val kafkaConsumer: KafkaConsumer<String, String>
) {

    private val offsetMap = HashMap<TopicPartition, OffsetAndMetadata>()

    fun consume(topic: String, messageQuantity: Int) {
        kafkaConsumer.subscribe(arrayListOf(topic))
        val messages = kafkaConsumer.poll(ofSeconds(5))
        val totalMessages = messages.count()
        val offset = kafkaConsumer.position(kafkaConsumer.assignment().first())

        LOG.info("Messages in the topic: $totalMessages")
        LOG.info("Current Offset: $offset")

        messages.take(messageQuantity).forEach {
            offsetMap[TopicPartition(it.topic(), it.partition())] = OffsetAndMetadata(it.offset() + 1)

            LOG.info("Message content: ${it.value()}")
            LOG.info("Message key: ${it.key()}")
            LOG.info("Message offset: ${it.offset()}")
            LOG.info("Message partition: ${it.partition()}")
        }

        kafkaConsumer.commitSync(offsetMap)
        kafkaConsumer.unsubscribe()
    }

    companion object {
        private val LOG = KotlinLogging.logger("consumer-logger")
    }
}

