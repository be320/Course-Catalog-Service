package com.bm.coursecatalogservice.controller

import com.bm.coursecatalogservice.service.GreetingService
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greetings")
class GreetingController @Autowired constructor(private val greetingService: GreetingService) {

    companion object : KLogging()

    @Value("\${MESSAGE}")
    lateinit var message : String

    @GetMapping("/{name}")
    fun retrieveGreeting(@PathVariable name: String) :String {
        logger.info("Hey Bitchhhhhhhhhh")
        return greetingService.retrieveGreeting(name, message)
    }
}