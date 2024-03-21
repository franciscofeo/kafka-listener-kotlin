package com.franciscofeo.kafkadltconsumer.usecases

import mu.KotlinLogging
import org.apache.kafka.clients.admin.KafkaAdminClient
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.time.Duration.ofSeconds

@Component
class ConsumeMessages(
    @Qualifier("consumer")
    private val kafkaConsumer: KafkaConsumer<String, String>
) {


    private val offsetMap = OffsetInfo()

    fun consume(topic: String, messageQuantity: Int) {
        kafkaConsumer.subscribe(arrayListOf(topic))
        val messages = kafkaConsumer.poll(ofSeconds(5))
        if (messages.isEmpty) return

        val offset = kafkaConsumer.position(kafkaConsumer.assignment().first())

        LOG.info("Messages in the topic: ${messages.count()}")
        LOG.info("Current Offset: $offset")

        LOG.info("-----------------------------------------------")
        messages.take(messageQuantity).forEach {
            LOG.info("Message content: ${it.value()}")
            LOG.info("Message key: ${it.key()}")
            LOG.info("Message offset: ${it.offset()}")
            LOG.info("Message partition: ${it.partition()}")

            offsetMap[TopicPartition(it.topic(), it.partition())] = OffsetAndMetadata(it.offset() + 1)
        }
        LOG.info("-----------------------------------------------")


        kafkaConsumer.commitSync(offsetMap)
        kafkaConsumer.unsubscribe()
    }

    companion object {
        private val LOG = KotlinLogging.logger("consumer-logger")
    }
}

typealias OffsetInfo = HashMap<TopicPartition, OffsetAndMetadata>

