package com.franciscofeo.kafkadltconsumer.controller

import com.franciscofeo.kafkadltconsumer.usecases.ConsumeMessages
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/consume")
@RestController
class ConsumeMessageController(
    val consumeMessages: ConsumeMessages
) {

    @PostMapping
    fun consume(@RequestParam topicName: String) {
        consumeMessages.consume(topicName)
    }

}