package com.bm.coursecatalogservice.controller

import com.bm.coursecatalogservice.service.GreetingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
class GreetingControllerUnitTest @Autowired constructor(private val webTestClient: WebTestClient) {

    @Value("\${MESSAGE}")
    lateinit var message: String

    @MockkBean
    lateinit var greetingServiceMock: GreetingService

    @Test
    fun retrieveGreeting() {
        val name = "Ahmed"

        every { greetingServiceMock.retrieveGreeting(any(), any()) } returns "$name, $message"

        val result = webTestClient.get()
            .uri("/v1/greetings/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals("$name, $message", result.responseBody)
    }
}

