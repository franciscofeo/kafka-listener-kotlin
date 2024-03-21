package com.franciscofeo.kafkadltconsumer.usecases

import mu.KotlinLogging
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.RecordsToDelete
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class ConsumeAllMessages(
    @Qualifier("consumer2")
    private val kafkaConsumer: KafkaConsumer<String, String>,
    private val adminClient: AdminClient
) {

    fun consume(topic: String) {
        kafkaConsumer.subscribe(arrayListOf(topic))

        /**
         * Default config of max.poll.records is 500 records of each poll() call
         */
        val messages = kafkaConsumer.poll(Duration.ofSeconds(5))

        val currentOffset = kafkaConsumer.assignment().map { kafkaConsumer.position(it) }

        LOG.info("Messages in the topic: ${messages.count()}")
        LOG.info("Current offset: $currentOffset")

        LOG.info("-----------------------------------------------")
        messages.forEach {
            LOG.info("Message content: ${it.value()}")
            LOG.info("Message key: ${it.key()}")
            LOG.info("Message offset: ${it.offset()}")
            LOG.info("Message partition: ${it.partition()}")

            removeRecord(it)
        }
        LOG.info("-----------------------------------------------")

        kafkaConsumer.commitSync()
        kafkaConsumer.unsubscribe()
    }

    private fun removeRecord(record: ConsumerRecord<String, String>) {
        val recordsToDelete = RecordsToDelete.beforeOffset(record.offset() + 1) // OFFSET NUMBER STARTS AT 0 INDEX
        val tp = TopicPartition(record.topic(), record.partition())
        adminClient.deleteRecords(mapOf(tp to recordsToDelete))
    }

    companion object {
        private val LOG = KotlinLogging.logger("consumer-logger")
    }
}