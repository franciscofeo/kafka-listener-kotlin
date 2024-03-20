package com.franciscofeo.kafkadltconsumer.controller

import com.franciscofeo.kafkadltconsumer.usecases.ConsumeAllMessages
import com.franciscofeo.kafkadltconsumer.usecases.ConsumeMessages
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/consume")
@RestController
class ConsumeMessageController(
    val consumeMessages: ConsumeMessages,
    val consumeAllMessages: ConsumeAllMessages
) {

    @PostMapping
    fun consume(@RequestParam topicName: String, @RequestParam(value = "qtd") messageQuantity: Int) {
        consumeMessages.consume(topicName, messageQuantity)
    }

    @PostMapping("/all")
    fun consumeAll(@RequestParam topicName: String) {
        consumeAllMessages.consume(topicName)
    }


}