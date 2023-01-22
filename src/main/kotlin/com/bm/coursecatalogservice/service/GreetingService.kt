package com.bm.coursecatalogservice.service

import org.springframework.stereotype.Service

@Service
class GreetingService {

    fun retrieveGreeting(name: String, message: String) = "$name, $message"
}