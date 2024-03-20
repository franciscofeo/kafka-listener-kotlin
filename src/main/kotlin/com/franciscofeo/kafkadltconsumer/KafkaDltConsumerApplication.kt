package com.franciscofeo.kafkadltconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaDltConsumerApplication

fun main(args: Array<String>) {
    runApplication<KafkaDltConsumerApplication>(*args)
}
